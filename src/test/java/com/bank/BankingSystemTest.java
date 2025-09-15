package com.bank;

import com.bank.domain.Account;
import com.bank.domain.Customer;
import com.bank.enums.KycStatus;
import com.bank.repository.AccountRepository;
import com.bank.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTest {

    

    private AccountRepository auditService;
    private DepositService depositService;
    private WithdrawService withdrawService;
    private TransferService transferService;

    private Customer alice;
    private Customer bob;
    private Account aliceAcc;
    private Account bobAcc;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/atlas_capstone", "root", "anupam1234"
        );
        
        auditService = new AccountRepository(conn);
        depositService = new DepositService(auditService);
        withdrawService = new WithdrawService(auditService);
        transferService = new TransferService(auditService);

        alice = new Customer("Alice", "alice@example.com", KycStatus.VERIFIED);
        bob = new Customer("Bob", "bob@example.com", KycStatus.VERIFIED);

        aliceAcc = new Account(alice.getCustomerId(), "USD");
        bobAcc = new Account(bob.getCustomerId(), "USD");
    }

    @Test
    void testDepositIncreasesBalance() {
        depositService.deposit(aliceAcc, BigDecimal.valueOf(500), "Alice");
        assertEquals(BigDecimal.valueOf(500), aliceAcc.getBalance());
    }

    @Test
    void testWithdrawDecreasesBalance() {
        depositService.deposit(aliceAcc, BigDecimal.valueOf(300), "Alice");
        withdrawService.withdraw(aliceAcc, BigDecimal.valueOf(100), "Alice");
        assertEquals(BigDecimal.valueOf(200), aliceAcc.getBalance());
    }

    @Test
    void testWithdrawFailsIfInsufficientFunds() {
        depositService.deposit(aliceAcc, BigDecimal.valueOf(50), "Alice");
        withdrawService.withdraw(aliceAcc, BigDecimal.valueOf(200), "Alice");
        // Balance should remain unchanged
        assertEquals(BigDecimal.valueOf(50), aliceAcc.getBalance());
    }

    @Test
    void testTransferMovesFundsBetweenAccounts() {
        depositService.deposit(aliceAcc, BigDecimal.valueOf(400), "Alice");
        transferService.transfer(aliceAcc, bobAcc, BigDecimal.valueOf(150), "Alice");

        assertEquals(BigDecimal.valueOf(250), aliceAcc.getBalance());
        assertEquals(BigDecimal.valueOf(150), bobAcc.getBalance());
    }

    @Test
    void testTransferFailsIfInsufficientFunds() {
        depositService.deposit(aliceAcc, BigDecimal.valueOf(100), "Alice");
        transferService.transfer(aliceAcc, bobAcc, BigDecimal.valueOf(300), "Alice");

        // Balances should remain the same
        assertEquals(BigDecimal.valueOf(100), aliceAcc.getBalance());
        assertEquals(BigDecimal.ZERO, bobAcc.getBalance());
    }
}

