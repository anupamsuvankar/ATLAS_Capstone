package com.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.Customer;
import com.bank.enums.KycStatus;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.DepositService;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/atlas_capstone", "root", "anupam1234"
        );

        CustomerRepository customerRepo = new CustomerRepository(conn);
        AccountRepository accountRepo = new AccountRepository(conn);
        DepositService depositService = new DepositService(accountRepo);

        Customer alice = new Customer("Alice", "alice@gmnail.com", KycStatus.PENDING);
        customerRepo.insertCustomer(alice);

        Account aliceAccount = new Account(UUID.randomUUID(), customerRepo.getCustomerId(alice.getEmail()),0 , "INR");
        accountRepo.insertAccount(aliceAccount);

        depositService.deposit(aliceAccount, 500, "Alice");

        System.out.println("Deposit successful. New balance: " + aliceAccount.getBalance());
    }
}
