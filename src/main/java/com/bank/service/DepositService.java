package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.enums.TransactionStatus;
import java.math.BigDecimal;

public class DepositService {
    private AuditService auditService;

    public DepositService(AuditService auditService) {
        this.auditService = auditService;
    }

    public Transaction deposit(Account account, BigDecimal amount, String actor) {
        BigDecimal before = account.getBalance();
        account.credit(amount);

        Transaction tx = new Transaction(null, account.getAccountId(), amount, account.getCurrency());
        tx.setStatus(TransactionStatus.SUCCESS);

        auditService.log(tx, actor, "DEPOSIT", before, account.getBalance());
        return tx;
    }
}
