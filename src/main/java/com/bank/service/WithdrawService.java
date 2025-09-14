package com.bank.service;

import com.bank.domain.Account;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

import java.math.BigDecimal;

public class WithdrawService {
    private final AuditService auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public WithdrawService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void withdraw(Account account, BigDecimal amount, String actor) {
        BigDecimal before = account.getBalance();
        if (before.compareTo(amount) >= 0) {
            account.setBalance(before.subtract(amount));

            auditService.log(actor, "WITHDRAW", before, account.getBalance());

            auditRepo.insertAuditLog(actor, "WITHDRAW", before.doubleValue(), account.getBalance().doubleValue());
            txnRepo.insertTransaction(account.getAccountId(), null, amount.doubleValue(), "WITHDRAW");
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }
}
