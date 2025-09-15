package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

public class TransferService {
    private final AccountRepository auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public TransferService(AccountRepository accountRepo) {
        this.auditService = accountRepo;
    }

    public void transfer(Account from, Account to, int amount, String actor) {
        int beforeFrom = from.getBalance();

        if (beforeFrom-amount >= 0) {
            from.debit(amount);
            to.credit(amount);

            int afterFrom = from.getBalance();
            int afterTo = to.getBalance();

            Transaction debitTxn = new Transaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-DEBIT");
            Transaction creditTxn = new Transaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-CREDIT");

            auditService.log(debitTxn, actor, "TRANSFER-DEBIT", beforeFrom, afterFrom);
            auditService.log(creditTxn, actor, "TRANSFER-CREDIT", 0, afterTo);

            auditRepo.insertAuditLog(actor, "TRANSFER-DEBIT", beforeFrom, afterFrom);
            auditRepo.insertAuditLog(actor, "TRANSFER-CREDIT", 0, afterTo);

            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-DEBIT");
            txnRepo.insertTransaction(from.getAccountId(), to.getAccountId(), amount, "TRANSFER-CREDIT");
        } else {
            System.out.println("❌ Insufficient funds for transfer.");
        }
    }
}
