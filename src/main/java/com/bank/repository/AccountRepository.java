package com.bank.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.Transaction;

public class AccountRepository {

    private final Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    public void insertAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account (account_id, customer_id, balance, currency) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Generate UUID for account_id if null
            if (account.getAccountId() == null || account.getAccountId().isEmpty()) {
                account.setAccountId(UUID.randomUUID().toString());
            }

            stmt.setString(1, account.getAccountId());
            stmt.setString(2, account.getCustomerId().toString());
            stmt.setInt(3, account.getBalance());
            stmt.setString(4, account.getCurrency() != null ? account.getCurrency() : "INR");

            stmt.executeUpdate();
        }
    }

    public void log(Transaction txn, String actor, String string, int before, int after) {
        // TODO Auto-generated method stub
        
    }

    public Account getAccountById(String accountId) {
        return null;
    }
}