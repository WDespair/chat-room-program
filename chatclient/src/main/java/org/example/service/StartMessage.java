package org.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.example.service.SendMessage.sendMessage;

public class StartMessage {
    public static void startSend(BufferedReader reader) throws IOException {
        System.out.println("Starting Send Message");
//        System.out.println("系统默认编码: " + Charset.defaultCharset().name());

        String message;

        while ((message = reader.readLine()) != null) {
            if (message.contains("\uFFFD")) {
                System.err.println("警告: 检测到无效字符，可能是编码问题");
            }
            sendMessage(message);
        }
    }
}
