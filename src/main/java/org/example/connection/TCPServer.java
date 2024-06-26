package org.example.connection;

import org.example.Dto.CommandRequest;
import org.example.Dto.Response;
import org.example.Dto.StatusCode;
import org.example.controller.CommandController;
import org.example.utils.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class TCPServer {
    private static final int BUFFER_SIZE = 4096;
    private ByteBuffer buffer;

    private static final String HOST = "localhost";
    private static final int PORT = 2000;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private final CommandController commandController;

    public TCPServer() {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        commandController = new CommandController();
    }

    public void openConnection() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        serverSocketChannel.bind(inetSocketAddress);
        selector = initSelector();
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();
        serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
        return socketSelector;
    }

    public void run() {
        try {
            while (true) {
                selector.selectNow();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = takeKey(selectedKeys);
                    handleKey(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SelectionKey takeKey(Iterator<SelectionKey> selectionKeyIterator) {
        SelectionKey key = selectionKeyIterator.next();
        selectionKeyIterator.remove();
        return key;
    }

    private void handleKey(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                accept(key);
            } else if (key.isReadable()) {
                read(key);
            } else if (key.isWritable()) {
                write(key);
            }
        }
    }
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        System.out.println("Подключенно: " + socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.clear();
        int bytesRead;
        try {
            bytesRead = socketChannel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (bytesRead == -1) {
            key.cancel();
            return;
        }
        this.buffer.flip();

        CommandRequest request = Serializer.deserializeObject(buffer);

        String commandResponse = commandController.executeCommand(
                request.getCommandName(),
                request.getCommandObjectArgument(),
                request.getCommandStringArgument()
        );

        Response response = new Response(StatusCode._200_SUCCESS_, commandResponse);

        socketChannel.register(this.selector, SelectionKey.OP_WRITE, response);
    }
    public void close() throws IOException {
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();

        ByteBuffer writeBuffer = Serializer.serializeObject(response);
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }

        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
