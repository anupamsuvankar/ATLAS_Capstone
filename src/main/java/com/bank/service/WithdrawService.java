package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
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
            account.debit(amount);
            BigDecimal after = account.getBalance();

            Transaction txn = new Transaction(account.getAccountId(), null, amount, "WITHDRAW");

            auditService.log(txn, actor, "WITHDRAW", before, after);

            auditRepo.insertAuditLog(actor, "WITHDRAW", before.doubleValue(), after.doubleValue());
            txnRepo.insertTransaction(account.getAccountId(), null, amount.doubleValue(), "WITHDRAW");
        } else {
            System.out.println("❌ Insufficient funds for withdrawal.");
        }
    }
}
