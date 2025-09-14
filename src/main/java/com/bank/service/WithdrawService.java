package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.enums.TransactionStatus;
import java.math.BigDecimal;

public class WithdrawService {
    private AuditService auditService;

    public WithdrawService(AuditService auditService) {
        this.auditService = auditService;
    }

    public Transaction withdraw(Account account, BigDecimal amount, String actor) {
        BigDecimal before = account.getBalance();
        try {
            account.debit(amount);
            Transaction tx = new Transaction(account.getAccountId(), null, amount, account.getCurrency());
            tx.setStatus(TransactionStatus.SUCCESS);
            auditService.log(tx, actor, "WITHDRAW", before, account.getBalance());
            return tx;
        } catch (IllegalArgumentException e) {
            Transaction tx = new Transaction(account.getAccountId(), null, amount, account.getCurrency());
            tx.setStatus(TransactionStatus.FAILED);
            auditService.log(tx, actor, "WITHDRAW-FAILED", before, account.getBalance());
            return tx;
        }
    }
}
