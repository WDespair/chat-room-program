package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.constant.TimeSchedule.scheduler;
import static org.example.service.ReceiveMessage.receiveMessage;
import static org.example.service.Register.userRegistered;
import static org.example.service.StartMessage.startSend;


public class ChatClientMain {

    public static AtomicReference<String> ID = new AtomicReference<>();

    public static void main(String[] args) throws IOException {

        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new java.io.PrintStream(System.err, true, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8)
        );
        System.out.print("Enter ID: ");
        String id = reader.readLine().trim();
        ID.set(id);
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
        }, 0, 5, TimeUnit.SECONDS);

        startSend(reader);

    }
}
