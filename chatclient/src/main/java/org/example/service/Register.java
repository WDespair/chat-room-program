package org.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static org.example.constant.ServerUrl.CHAT_SERVER_URL;

public class Register {
    public static void userRegistered(String id) {
        try {
            URL url = new URL(CHAT_SERVER_URL + "/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            String iddata = "user=" + URLEncoder.encode(id, StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] bytes = iddata.getBytes(StandardCharsets.UTF_8);
                os.write(bytes, 0, bytes.length);
            }

            if (connection.getResponseCode() == 200) {

            } else {
                System.out.println(connection.getResponseCode());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
