package com.adaysoft.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
@SuppressWarnings("unchecked")
public class MainClient {
    
    public static void main(String[] args) {

        try (Socket s = new Socket("localhost", 8080);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Scanner sc = new Scanner(System.in)) {
            
            List<String> messagesListSync = (List<String>) ois.readObject();
            
            if (!messagesListSync.isEmpty()) {
               receiveMessageList(messagesListSync);
            }

            System.out.println("Enter name: ");
            String name = sc.nextLine();
            oos.writeObject(name);

            String message = "";
            while (!message.equals("bye")) {
                System.out.println("Enter a message: ");
                message = sc.nextLine();
                oos.writeObject(message);

                String messagefromServer = (String) ois.readObject();
                System.out.println(messagefromServer);   
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receiveMessageList(List<String> messagesListSync) {
        synchronized (messagesListSync) {
            for (String msg : messagesListSync) {
                System.out.println(msg);
            }
        } 
    }
}
