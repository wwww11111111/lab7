package smyts.lab6.client;

import smyts.lab6.common.util.Request;
import smyts.lab6.common.util.Response;
import smyts.lab6.common.util.UserData;
import smyts.lab6.utils.*;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    private static String login;
    private static String password;

    public static void main(String[] args) throws ClassNotFoundException {
        authorizationFetch();
    }

    public static void authorizationFetch() throws ClassNotFoundException {
        ClientConnectionWorker clientConnectionWorker = new ClientConnectionWorker();
        Selector selector;
        boolean authorized = false;
        try {
            clientConnectionWorker.init();
            selector = Selector.open();
            clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            System.out.println("Some errors with connection has occurred, try again later.");
            shutDownProcess();
            return;
        }

        Authorizator authorizator = new Authorizator();
        ResponseHandler responseHandler = new ResponseHandler();

        while (!authorized) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isWritable()) {
                    Request request = authorizator.makeRequest();
                    saveSessionSettings(request.getUserData().getLogin(), request.getUserData().getPassword());
                    clientConnectionWorker.sendRequest(request);
                    clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    Response response = clientConnectionWorker.getResponse();

                    if (response.getMessage().equals("success")) {
                        authorized = true;
                        continue;
                    }

                    responseHandler.getResponseInfo(response);
                    clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_WRITE);
                }
            } catch (IOException e) {
                System.out.println("server is not available now, try again later.");
                shutDownProcess();
            }
        }
        executionFetch(clientConnectionWorker);

    }

    public static void executionFetch(ClientConnectionWorker clientConnectionWorker) throws ClassNotFoundException{
        RouteFactory routeFactory = new RouteFactory(new ScannerFieldsGetter(new Scanner(System.in)));
        CommandReader commandReader = new CommandReader();
        commandReader.init();
        Selector selector = null;
        try {
            selector = Selector.open();
            clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            System.out.println("Some errors with connection has occurred, try again later.");
            shutDownProcess();
        }

        ResponseHandler responseHandler = new ResponseHandler();

        boolean active = true;
        Request lastRequest = null;
        boolean needObject = false;

        while (active) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isReadable()) {
                    Response response = clientConnectionWorker.getResponse();
                    if (response.isObjectNeeded()) {
                        needObject = true;
                    }
                    responseHandler.getResponseInfo(response);

                    clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    Request request;
                    if (needObject) {
                        request = lastRequest;
                        request.setRoute(routeFactory.start());
                        needObject = false;
                        clientConnectionWorker.sendRequest(request);
                        clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_READ);
                        continue;
                    }

                    String command = commandReader.getCommand();
                    if (command.equalsIgnoreCase("exit")) {
                        clientConnectionWorker.getClientChannel().close();
                        active = false;
                        continue;
                    }

                    request = makeRequest();
                    request.setCommandNameAndArguments(command);
                    clientConnectionWorker.sendRequest(request);
                    lastRequest = request;
                    clientConnectionWorker.getClientChannel().register(selector, SelectionKey.OP_READ);
                }
            } catch (IOException e) {
                active = false;
                System.out.println("Some errors with connection has occurred. Try to restart the application or come back later.");
            }
        }
    }

    public static void shutDownProcess() {
        System.out.println("Good bye!");
        System.exit(1);
    }

    public static Request makeRequest() {
        Request request = new Request();
        request.setUserData(new UserData(login, password));
        return request;
    }

    public static void saveSessionSettings(String login, String password) {
        Client.login = login;
        Client.password = password;
    }
}

