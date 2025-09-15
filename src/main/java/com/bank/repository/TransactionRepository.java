package com.bank.repository;

import com.bank.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionRepository {

    public void insertTransaction(String fromAcc, String toAcc, double amount, String type) {
        String sql = "INSERT INTO transaction (transaction_id, from_account_id, to_account_id, amount, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, fromAcc);
            ps.setString(3, toAcc); 
            ps.setDouble(4, amount);
            ps.setString(5, type);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
