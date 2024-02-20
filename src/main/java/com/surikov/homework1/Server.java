package com.surikov.homework1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

    public static int userCounter;

    public Server(){
        userCounter = 0;
    }

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(Util.PORT);
            File logFile = new File("src/main/resources/logConnection.txt");
            FileWriter fileWriter = new FileWriter(logFile, true);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

            if (!(logFile.exists())) {
                logFile.createNewFile();
            }

            while (true) {
                Socket socket = serverSocket.accept();
                userCounter++;
                System.out.println(socket.getInetAddress().getHostName() + " is connected. Date: " + simpleDateFormat.format(date));
                fileWriter.write(socket.getInetAddress().getHostName() + " is connected. Date: " + simpleDateFormat.format(date) + "\n");
                fileWriter.flush();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
