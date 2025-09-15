package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;

public class DepositService {
    private final AccountRepository auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public DepositService(AccountRepository accountRepo) {
        this.auditService = accountRepo;
    }

    public void deposit(Account account, int amount, String actor) {
        int before = account.getBalance();

        account.credit(amount);

        int after = account.getBalance();

        Transaction txn = new Transaction(account.getAccountId(), null, amount, "INR");

        auditService.log(txn, actor, "DEPOSIT", before, after);

        auditRepo.insertAuditLog(actor, "DEPOSIT", before, after);
        txnRepo.insertTransaction(account.getAccountId(), null, amount, "DEPOSIT");
    }
}
