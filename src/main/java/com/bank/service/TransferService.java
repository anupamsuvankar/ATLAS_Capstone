package com.bank.service;

import com.bank.domain.Account;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

import java.math.BigDecimal;

public class TransferService {
    private final AuditService auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public TransferService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void transfer(Account from, Account to, BigDecimal amount, String actor) {
        BigDecimal beforeFrom = from.getBalance();
        if (beforeFrom.compareTo(amount) >= 0) {
            from.setBalance(beforeFrom.subtract(amount));
            to.setBalance(to.getBalance().add(amount));

            auditService.log(actor, "TRANSFER-DEBIT", beforeFrom, from.getBalance());
            auditService.log(actor, "TRANSFER-CREDIT", BigDecimal.ZERO, to.getBalance());

            auditRepo.insertAuditLog(actor, "TRANSFER-DEBIT", beforeFrom.doubleValue(), from.getBalance().doubleValue());
            auditRepo.insertAuditLog(actor, "TRANSFER-CREDIT", 0, to.getBalance().doubleValue());

            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount.doubleValue(), "TRANSFER-DEBIT");
            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount.doubleValue(), "TRANSFER-CREDIT");
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }
}
