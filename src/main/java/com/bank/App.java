package com.bank;

import com.bank.domain.Customer;
import com.bank.domain.Account;
import com.bank.enums.KycStatus;
import com.bank.service.*;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        AuditService auditService = new AuditService();
        DepositService depositService = new DepositService(auditService);
        WithdrawService withdrawService = new WithdrawService(auditService);
        TransferService transferService = new TransferService(auditService);

        Customer alice = new Customer("Alice", "alice@example.com", KycStatus.VERIFIED);
        Customer bob = new Customer("Bob", "bob@example.com", KycStatus.VERIFIED);

        Account aliceAcc = new Account(alice.getCustomerId(), "USD");
        Account bobAcc = new Account(bob.getCustomerId(), "USD");

        System.out.println("Initial Balances: Alice=" + aliceAcc.getBalance() + ", Bob=" + bobAcc.getBalance());

        depositService.deposit(aliceAcc, BigDecimal.valueOf(500), "Alice");
        withdrawService.withdraw(aliceAcc, BigDecimal.valueOf(100), "Alice");
        transferService.transfer(aliceAcc, bobAcc, BigDecimal.valueOf(200), "Alice");

        System.out.println("Final Balances: Alice=" + aliceAcc.getBalance() + ", Bob=" + bobAcc.getBalance());
    }
}
