package org.example.service;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.example.constant.ServerUrl.CHAT_SERVER_URL;

public class ReceiveMessage {

    private static Gson gson = new Gson();

    public static void receiveMessage(String user) throws IOException {
        try {
            URL url = new URL(CHAT_SERVER_URL + "?user=" + URLEncoder.encode(user, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                    String json = reader.readLine();
                    String[] messages = gson.fromJson(json, String[].class);

                    for (String m : messages) {
                        System.out.println(m);
                    }
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
