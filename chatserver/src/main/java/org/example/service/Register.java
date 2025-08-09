package org.example.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

import static org.example.constant.ChatMysql.*;
import static org.example.service.Closeservice.closeResources;

public class Register {
    public static void idRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");

        if (user == null || user.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "用户名不能为空");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            ps = conn.prepareStatement("SELECT user_name FROM register WHERE user_name = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                response.getWriter().write("用户名已存在：" + user);
                System.out.println("用户名已存在ok");
                return;
            }

            ps = conn.prepareStatement("INSERT INTO register (user_name) VALUES (?)");
            ps.setString(1, user);
            ps.executeUpdate();
            response.getWriter().write("注册成功！用户名：" + user);

        } catch (SQLException e) {
            System.err.println("===== 注册时发生异常 =====");
            e.printStackTrace();
            response.sendError(500, "注册失败");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "注册失败：" + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }
}
