package smyts.lab6.utils;

import smyts.lab6.common.util.Request;
import smyts.lab6.common.util.Response;
import smyts.lab6.common.util.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientConnectionWorker {

    private SocketChannel clientChannel;

    public void init() throws IOException {
        SocketAddress clientAddress = new InetSocketAddress(InetAddress.getLocalHost(), 1234);
        SocketChannel clientChannel = SocketChannel.open(clientAddress);
        clientChannel.configureBlocking(false);
        this.clientChannel = clientChannel;
        System.out.println("Connected");
    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }

    public void sendRequest(Request request) throws IOException {
        clientChannel.write(Serializer.serializeRequest(request));
    }

    public Response getResponse() throws IOException, ClassNotFoundException {
        int capacity = 7000;
        ByteBuffer temporaryBuffer;
        ByteBuffer bufferToSerialize = ByteBuffer.allocate(0);

        int numberOfBytes;
        do {
            temporaryBuffer = ByteBuffer.allocate(capacity);
            numberOfBytes = clientChannel.read(temporaryBuffer);
            temporaryBuffer.flip();
            bufferToSerialize.flip();

            bufferToSerialize = ByteBuffer.allocate(bufferToSerialize.capacity() + capacity).put(bufferToSerialize).put(temporaryBuffer);
        } while (numberOfBytes == capacity);

        return Serializer.deSerializeResponse(bufferToSerialize.array());
    }
}
