package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositService {
    private final AuditService auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public DepositService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void deposit(Account account, BigDecimal amount, String actor) {
        BigDecimal before = account.getBalance();

        // update in-memory balance (use credit instead of setBalance)
        account.credit(amount);

        BigDecimal after = account.getBalance();

        // create transaction
        Transaction txn = new Transaction(account.getAccountId(), null, amount, "DEPOSIT");

        // in-memory audit
        auditService.log(txn, actor, "DEPOSIT", before, after);

        // persist to DB
        auditRepo.insertAuditLog(actor, "DEPOSIT", before.doubleValue(), after.doubleValue());
        txnRepo.insertTransaction(account.getAccountId(), null, amount.doubleValue(), "DEPOSIT");
    }
}
