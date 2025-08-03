package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.example.constant.TimeSchedule.scheduler;
import static org.example.service.ReceiveMessage.receiveMessage;
import static org.example.service.SendMessage.sendMessage;
import static org.example.service.StartMessage.startSend;

public class ChatClientMain {
    public static String ID;

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        ID = scanner.nextLine().trim();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                receiveMessage(ChatClientMain.ID);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);

        startSend();
    }

}