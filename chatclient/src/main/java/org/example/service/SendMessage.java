package org.example.service;

import org.example.ChatClientMain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.example.constant.ServerUrl.CHAT_SERVER_URL;

public class SendMessage {
    public static void sendMessage(String message) throws IOException {
        try {
            // 打印原始消息
//            System.out.println("原始发送消息: " + message);
            // 打印编码格式
//            System.out.println("使用的字符编码: " + StandardCharsets.UTF_8.name());
//            System.out.println("原始消息字节信息: " + Arrays.toString(message.getBytes(StandardCharsets.UTF_8)));

            URL url = new URL(CHAT_SERVER_URL + "/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
            String data = "user=" + ChatClientMain.ID + "&message=" + encodedMessage;

//            System.out.println("编码后的消息: " + encodedMessage);
//            System.out.println("发送到服务器的完整数据: " + data);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(data.getBytes(StandardCharsets.UTF_8));
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            } else {
                System.out.println(connection.getResponseCode());
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
