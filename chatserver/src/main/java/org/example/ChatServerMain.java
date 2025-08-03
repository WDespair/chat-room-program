package org.example;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

import static org.example.constant.CharacterAarray.chatCache;
import org.example.service.getRequest;
import org.example.service.postRequest;


@WebServlet("/chat")
public class ChatServerMain extends HttpServlet {

    @Override
    public void init() {
        chatCache.put("A", new LinkedBlockingQueue<>());
        chatCache.put("B", new LinkedBlockingQueue<>());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getRequest.getrool(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        postRequest.postroll(request, response);
    }

}
