package org.example.service;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.constant.ChatMysql.*;
import static org.example.service.Closeservice.closeResources;

public class getRequest {

    public static void getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String receiver = request.getParameter("user");

        if (receiver == null || receiver.trim().isEmpty()) {
            response.sendError(400, "接收者不能为空");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 校验接收者是否已注册
            ps = conn.prepareStatement("SELECT user_name FROM register WHERE user_name = ?");
            ps.setString(1, receiver);
            rs = ps.executeQuery();
            if (!rs.next()) {
                response.sendError(404, "用户未注册，请先注册");
                return;
            }

            // 查询未读消息（逻辑不变）
            ps = conn.prepareStatement(
                    "SELECT sender, chat_content FROM chat_message " +
                            "WHERE receiver = ? AND is_read = 0 " +
                            "ORDER BY chat_time ASC"
            );
            ps.setString(1, receiver);
            rs = ps.executeQuery();

            List<String> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(rs.getString("sender") + ": " + rs.getString("chat_content"));
            }

            // 标记消息为已读
            ps = conn.prepareStatement("UPDATE chat_message SET is_read = 1 WHERE receiver = ? AND is_read = 0");
            ps.setString(1, receiver);
            ps.executeUpdate();

            // 返回消息
            response.setContentType("application/json; charset=utf-8");
            String json = new Gson().toJson(messages);
            System.out.flush();
            response.getWriter().print(json);

        } catch (SQLException e) {
            System.err.println("获取消息时SQL异常：" + e.getMessage());
            e.printStackTrace();
            response.sendError(500, "获取消息失败：" + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
    }
}
