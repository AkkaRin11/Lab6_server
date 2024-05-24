package org.example;

import org.example.Connection.TCPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.openConnection();
            tcpServer.run();
        } catch (IOException e) {
            System.out.println("до связи");
        }

    }
}