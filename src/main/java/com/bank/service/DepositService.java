package com.bank.service;

import com.bank.domain.Account;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

import java.math.BigDecimal;

public class DepositService {
    private final AuditService auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public DepositService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void deposit(Account account, BigDecimal amount, String actor) {
        BigDecimal before = account.getBalance();
        account.setBalance(before.add(amount));

        // In-memory audit
        auditService.log(actor, "DEPOSIT", before, account.getBalance());

        // DB persistence
        auditRepo.insertAuditLog(actor, "DEPOSIT", before.doubleValue(), account.getBalance().doubleValue());
        txnRepo.insertTransaction(account.getAccountId(), null, amount.doubleValue(), "DEPOSIT");
    }
}
