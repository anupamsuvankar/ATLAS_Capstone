package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.enums.TransactionStatus;
import java.math.BigDecimal;

public class TransferService {
    private AuditService auditService;

    public TransferService(AuditService auditService) {
        this.auditService = auditService;
    }

    public Transaction transfer(Account from, Account to, BigDecimal amount, String actor) {
        BigDecimal beforeFrom = from.getBalance();
        BigDecimal beforeTo = to.getBalance();

        try {
            from.debit(amount);
            to.credit(amount);

            Transaction tx = new Transaction(from.getAccountId(), to.getAccountId(), amount, from.getCurrency());
            tx.setStatus(TransactionStatus.SUCCESS);

            auditService.log(tx, actor, "TRANSFER-DEBIT", beforeFrom, from.getBalance());
            auditService.log(tx, actor, "TRANSFER-CREDIT", beforeTo, to.getBalance());

            return tx;
        } catch (Exception e) {
            Transaction tx = new Transaction(from.getAccountId(), to.getAccountId(), amount, from.getCurrency());
            tx.setStatus(TransactionStatus.FAILED);
            auditService.log(tx, actor, "TRANSFER-FAILED", beforeFrom, from.getBalance());
            return tx;
        }
    }
}
