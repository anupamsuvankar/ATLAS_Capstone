package com.bank.domain;

import com.bank.enums.AccountStatus;
import java.util.UUID;

public class Account {
    private UUID accountId;
    private UUID customerId;
    private int balance;
    private String currency;
    private AccountStatus status;

    public Account(UUID customerId, UUID custId, int balance, String currency) {
        this.accountId = UUID.randomUUID();
        this.customerId = custId;
        this.balance = balance;
        this.currency = currency;
        this.status = AccountStatus.ACTIVE;
    }



    public Account(UUID customerId, String currency) {
        this.accountId = UUID.randomUUID();
        this.customerId = customerId;
        this.balance = 0;
        this.currency = currency;
        this.status = AccountStatus.PENDING;
    }



    public String getAccountId() {
        return accountId.toString();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public int getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void debit(int amount) {
        balance = balance-amount;
    }

    public void credit(int amount) {
        balance = balance+amount;
    }

    public void setAccountId(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAccountId'");
    }
}
