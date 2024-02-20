package com.surikov.homework1;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ServerThread extends Thread {

    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private File logFile;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private String hostName;
    private Scanner scanner;

    public ServerThread(Socket socket) {
        try {
            hostName = socket.getInetAddress().getHostName(); //It's need for logFile
            printStream = new PrintStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            logFile = new File("src/main/resources/logDisconected.txt");
            fileWriter = new FileWriter(logFile,true);
            date = new Date();
            simpleDateFormat = new SimpleDateFormat();
            scanner = new Scanner(System.in);

            if(!(logFile.exists())) {
                logFile.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        int countQuotesForOneUser = 0;

        try {
            if(Server.userCounter>Util.MAX_COUNT_USERS){
                printStream.println("Disconnect");
            } else {
                while (!(bufferedReader.readLine().equalsIgnoreCase("exit"))) {
                    int i = (int)(Math.random()*Quotes.quotes.size());
                    printStream.println(Quotes.quotes.get(i));
                    countQuotesForOneUser++;
                    if(countQuotesForOneUser>Util.MAX_QOUTES_FOR_ONE_USER){
                        printStream.println("Max quotes");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void disconnect() {

        try {
            Server.userCounter--;
            System.out.println(hostName + " is disconnected. Date: " + simpleDateFormat.format(date) + "\n");
            fileWriter.write( hostName + " is disconnected. Date: " + simpleDateFormat.format(date) + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(printStream!=null) {
            printStream.close();
        }

        if(bufferedReader!=null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
