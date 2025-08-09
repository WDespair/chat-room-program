package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
            String urlstring = CHAT_SERVER_URL + "?user=" + URLEncoder.encode(user, StandardCharsets.UTF_8);
//            System.out.println("拼接后的URL字符串：" + urlstring);
            URL url = new URL(urlstring);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    try {
                        String json = reader.readLine();
                        if (json == null || json.trim().isEmpty()) {
                            System.out.println("没有新消息");
                            return;
                        }
                        String[] messages = gson.fromJson(json, String[].class);
                        for (String m : messages) {
                            System.out.println(m);
                        }
                    } catch (JsonSyntaxException e) {
                        System.err.println("JSON解析错误：" + e.getMessage());
                    } catch (IOException e) {
                        System.err.println("网络错误：" + e.getMessage());
                    }
                } catch (JsonSyntaxException e) {
                    System.err.println("JSON解析错误：" + e.getMessage());
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("URL格式错误，无法创建连接。请检查CHAT_SERVER_URL是否正确: " + e);
        } catch (IOException e) {
            throw new RuntimeException("网络操作失败", e);
        }
    }

}
