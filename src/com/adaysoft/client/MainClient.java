package com.adaysoft.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {

        try (Socket s = new Socket("localhost", 8080);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Scanner sc = new Scanner(System.in)) {
            
            ArrayList<String> msgList = (ArrayList<String>) ois.readObject();
            
            
            if (!msgList.isEmpty()) {
                for (String msg : msgList) {
                    System.out.println(msg);
                }
            }

            System.out.println("Enter name: ");
            String name = sc.nextLine();
            oos.writeObject(name);

            String msg = "";
            while (!msg.equals("bye")) {
                System.out.println("Enter a message: ");
                msg = sc.nextLine();
                oos.writeObject(msg);

                String msgServer = (String) ois.readObject();
                if (msgServer != null) {
                    System.out.println(msgServer);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
