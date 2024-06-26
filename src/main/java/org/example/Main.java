package org.example;

import org.example.utils.NameUtil;
import org.example.connection.TCPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        NameUtil nameUtil = NameUtil.getInstance();
        nameUtil.setName("lab6.json");

        TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.openConnection();
            tcpServer.run();
        } catch (IOException e) {
            System.out.println("Сервер непредвиденно завершил свою работу");
        }

    }
}