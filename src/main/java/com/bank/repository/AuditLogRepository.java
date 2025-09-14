package com.bank.repository;

import com.bank.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class AuditLogRepository {

    public void insertAuditLog(String actor, String action, double before, double after) {
        String sql = "INSERT INTO audit_log (log_id, actor, action, before_balance, after_balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, actor);
            ps.setString(3, action);
            ps.setDouble(4, before);
            ps.setDouble(5, after);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
