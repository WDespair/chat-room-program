package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.example.constant.CharacterAarray.chatCache;

public class postRequest {
    public static void postroll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("user");
        String message = request.getParameter("message");

        if (user == null  || message == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        chatCache.forEach((k,v) -> {
            if (!k.equals(user)) {
                v.offer(user + ":" + message);
            }
        });

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
