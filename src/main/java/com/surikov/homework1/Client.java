package com.surikov.homework1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String choice;
        String responce;

        try(Socket socket = new Socket(InetAddress.getLocalHost(),Util.PORT);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (socket.isConnected()) {

                System.out.println("\nWhat do you want? (Enter number of action):\n1.Give me quotes\n2.Disconnect\n\n");
                choice = scan.nextLine();

                if(choice.equals("1")) {

                    printStream.println("Request");
                    responce = bufferedReader.readLine();

                    if(!(responce.equalsIgnoreCase("Disconnect"))){
                        if(!(responce.equalsIgnoreCase("Max quotes"))){
                            System.out.println(responce);
                        } else {
                            System.out.println("The maximum number of requests has been reached. Disconnect...");
                            break;
                        }
                    } else {
                        System.out.println("The maximum number of users " +
                                "on the server has been reached. Try to connect again");
                        break;
                    }
                }
                if(choice.equals("2")) {
                    printStream.println("exit");
                    break;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
