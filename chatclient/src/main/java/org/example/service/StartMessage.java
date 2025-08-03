package org.example.service;

import java.util.Scanner;

import static org.example.service.SendMessage.sendMessage;

public class StartMessage {
    public static void startSend() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String message = sc.nextLine();
                sendMessage(message);
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
