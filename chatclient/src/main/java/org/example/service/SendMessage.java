package org.example.service;

import org.example.ChatClientMain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.example.constant.ServerUrl.CHAT_SERVER_URL;

public class SendMessage {
    public static void sendMessage(String message) throws IOException {
        try {
            URL url = new URL(CHAT_SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            String data = "user=" + ChatClientMain.ID + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(data.getBytes(StandardCharsets.UTF_8));
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println(connection.getResponseCode());
            }


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
