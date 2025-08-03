package org.example.service;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.example.constant.CharacterAarray.chatCache;

public class getRequest {

    public static void getrool(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("user");

        if (user == null || !chatCache.containsKey(user)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<String> messages = new ArrayList<>();
        chatCache.get(user).drainTo(messages);

        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(new Gson().toJson(messages));
    }
}
