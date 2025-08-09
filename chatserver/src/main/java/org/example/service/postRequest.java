package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.constant.ChatMysql.*;
import static org.example.service.Closeservice.closeResources;

public class postRequest {
    public static void postMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sender = request.getParameter("user");
        String message = request.getParameter("message");

        // 参数校验
        if (sender == null || sender.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            response.sendError(400, "发送者和消息内容不能为空");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 校验发送者是否已注册
            ps = conn.prepareStatement("SELECT user_name FROM register WHERE user_name = ?");
            ps.setString(1, sender);
            rs = ps.executeQuery();
            if (!rs.next()) {
                response.sendError(404, "发送者未注册，请先注册");
                return;
            }

            // 查询所有已注册的接收者
            ps = conn.prepareStatement("SELECT user_name FROM register WHERE user_name != ?");
            ps.setString(1, sender);
            rs = ps.executeQuery();
            List<String> receivers = new ArrayList<>();
            while (rs.next()) {
                receivers.add(rs.getString("user_name"));
            }
            if (receivers.isEmpty()) {
                response.getWriter().write("没有在线用户，消息未发送");
                return;
            }

            // 插入消息到chat_message表
            ps = conn.prepareStatement(
                    "INSERT INTO chat_message (sender, receiver, chat_content) VALUES (?, ?, ?)"
            );
            for (String receiver : receivers) {
                ps.setString(1, sender);
                ps.setString(2, receiver);
                ps.setString(3, message);
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            response.getWriter().write("消息已发送给" + results.length + "个用户");

        } catch (SQLException e) {
            System.err.println("===== 注册时发生异常 =====");
            e.printStackTrace();
            response.sendError(500, "发送失败：" + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
    }
}
