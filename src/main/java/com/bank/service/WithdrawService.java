package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.AuditLogRepository;
import com.bank.repository.TransactionRepository;


public class WithdrawService {
    private final AccountRepository auditService;
    private final AuditLogRepository auditRepo = new AuditLogRepository();
    private final TransactionRepository txnRepo = new TransactionRepository();

    public WithdrawService(AccountRepository accountRepo) {
        this.auditService = accountRepo;
    }

    public void withdraw(Account account, int amount, String actor) {
        int before = account.getBalance();

        if (before-amount >= 0) {
            account.debit( amount);
            int after = account.getBalance();

            Transaction txn = new Transaction(account.getAccountId(), null, amount, "WITHDRAW");

            auditService.log(txn, actor, "WITHDRAW", before, after);

            auditRepo.insertAuditLog(actor, "WITHDRAW", before, after);
            txnRepo.insertTransaction(account.getAccountId(), null, amount, "WITHDRAW");
        } else {
            System.out.println("❌ Insufficient funds for withdrawal.");
        }
    }
}
