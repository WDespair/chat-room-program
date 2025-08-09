package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.constant.TimeSchedule.scheduler;
import static org.example.service.ReceiveMessage.receiveMessage;
import static org.example.service.Register.userRegistered;
import static org.example.service.StartMessage.startSend;


public class ChatClientMain {

    public static AtomicReference<String> ID = new AtomicReference<>();

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        ID.set(scanner.nextLine().trim());

        userRegistered(ID.get());

        scheduler.scheduleAtFixedRate(() -> {
            try {
                receiveMessage(ID.get());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("===== 运行时异常 =====");
                e.printStackTrace();
            } catch (Error e) {
                System.err.println("===== 捕获到Error =====");
                e.printStackTrace();
            } catch (Throwable t) {
                System.err.println("===== 捕获到Throwable =====");
                t.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

        startSend();
    }

}