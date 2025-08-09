package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.sql.SQLException;
import java.io.*;

import static org.example.service.Register.idRegistration;
import static org.example.service.getRequest.getMessage;
import static org.example.service.postRequest.postMessage;


@WebServlet("/chat/*")
public class ChatServerMain extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL驱动加载失败", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getMessage(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String pathInfo = request.getPathInfo();

        System.out.println("接收到的pathInfo：" + pathInfo);

        if ("/register".equals(pathInfo)) {
            // 处理注册请求
            idRegistration(request, response);
        } else if ("/send".equals(pathInfo)) {
            // 处理发送消息请求
            postMessage(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "请求路径不存在");
        }

    }

}
