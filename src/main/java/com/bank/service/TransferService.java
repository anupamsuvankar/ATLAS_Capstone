package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
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
            from.debit(amount);
            to.credit(amount);

            BigDecimal afterFrom = from.getBalance();
            BigDecimal afterTo = to.getBalance();

            Transaction debitTxn = new Transaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-DEBIT");
            Transaction creditTxn = new Transaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-CREDIT");

            auditService.log(debitTxn, actor, "TRANSFER-DEBIT", beforeFrom, afterFrom);
            auditService.log(creditTxn, actor, "TRANSFER-CREDIT", BigDecimal.ZERO, afterTo);

            auditRepo.insertAuditLog(actor, "TRANSFER-DEBIT", beforeFrom.doubleValue(), afterFrom.doubleValue());
            auditRepo.insertAuditLog(actor, "TRANSFER-CREDIT", 0, afterTo.doubleValue());

            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount.doubleValue(), "TRANSFER-DEBIT");
            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount.doubleValue(), "TRANSFER-CREDIT");
        } else {
            System.out.println("❌ Insufficient funds for transfer.");
        }
    }
}
