package com.bank.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.bank.domain.Customer;

public class CustomerRepository {
    private Connection conn;

    public CustomerRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean customerExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM customer WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insertCustomer(Customer customer) throws SQLException {
        if (customerExists(customer.getEmail())) return;
    
        String sql = "INSERT INTO customer (customer_id, name, email, kyc_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getCustomerId().toString());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getKycStatus().name());
            stmt.executeUpdate();
        }
    }

    public UUID getCustomerId(String email) throws SQLException {
        String sql = "SELECT customer_id FROM customer WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return UUID.fromString(rs.getString("customer_id"));
                
                throw new SQLException("Customer not found: " + email);
            }
        }
    }
}
