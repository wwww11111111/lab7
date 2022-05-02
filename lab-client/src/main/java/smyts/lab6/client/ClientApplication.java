//package smyts.lab6.client;
//
//import smyts.lab6.common.util.Request;
//import smyts.lab6.common.util.Response;
//import smyts.lab6.common.util.Serializer;
//import smyts.lab6.exceptions.EndOfFileException;
//import smyts.lab6.utils.*;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.SocketChannel;
//import java.util.Iterator;
//import java.util.Scanner;
//import java.util.Set;
//
//public class ClientApplication {
//    public static void start() throws IOException, EndOfFileException {
//
//        Scanner scanner = new Scanner(System.in);
//        RouteFactory routeFactory = new RouteFactory(new ScannerFieldsGetter(scanner), new ReaderFieldsGetter(null));
//        Selector selector = Selector.open();
//        SocketAddress clientAddress = new InetSocketAddress(InetAddress.getLocalHost(), 1234);
//        SocketChannel clientChannel = SocketChannel.open(clientAddress);
//        System.out.println("Connected!");
//        System.out.println("введите help чтобы увидеть список допустимых команд.");
//        clientChannel.configureBlocking(false);
//        clientChannel.register(selector, SelectionKey.OP_WRITE);
//        ResponseHandler responseHandler = new ResponseHandler();
//        UserInputHandler userInputHandler = new UserInputHandler();
//
//        while (true) {
//            selector.select();
//            Set<SelectionKey> keys = selector.selectedKeys();
//            Iterator<SelectionKey> iterator = keys.iterator();
//
//            while (iterator.hasNext()) {
//                SelectionKey key = iterator.next();
//                iterator.remove();
//                if (key.isReadable()) {
//                    SocketChannel serverChannel = (SocketChannel) key.channel();
//                    ByteBuffer buffer = ByteBuffer.allocate(4096);
//                    try {
//                        serverChannel.read(buffer);
//                        Response response = Serializer.deSerializeResponse(buffer.array());
//                        responseHandler.handleResponse(response);
//                        if (response.isObjectNeeded()) {
//                            Request request = new Request();
//                            request.setRoute(routeFactory.start(true));
//                            ByteBuffer bufferToSend = Serializer.serializeRequest(request);
//                            bufferToSend.clear();
//                            clientChannel.write(bufferToSend);
//                            clientChannel.register(selector, SelectionKey.OP_READ);
//                        } else {
//                            clientChannel.register(selector, SelectionKey.OP_WRITE);
//                        }
//                    } catch (IOException | ClassNotFoundException e) {
//                        System.out.println("Соединение прервано. Вернитесь позже или попробуйте перезагрузить приложение.");
//                        System.exit(1);
//                    }
//                } else if (key.isWritable()) {
//
//                    Request request = new Request();
//                    String command = userInputHandler.getCommand();
//                    if (command.equals("exit")) {
//                        System.exit(0);
//                    }
//                    request.setCommandNameAndArguments(command);
//                    try {
//                        ByteBuffer buffer = Serializer.serializeRequest(request);
//                        buffer.clear();
//                        clientChannel.write(buffer);
//                        clientChannel.register(selector, SelectionKey.OP_READ);
//                    } catch (IOException e) {
//                        System.out.println("Соединение прервано. Вернитесь позже или попробуйте перезагрузить приложение.");
//                        System.exit(1);
//                    }
//                }
//            }
//        }
//    }
//}
