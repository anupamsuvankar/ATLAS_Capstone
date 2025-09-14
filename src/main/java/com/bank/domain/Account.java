package com.bank.domain;

import com.bank.enums.AccountStatus;
import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private UUID accountId;
    private UUID customerId;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    public Account(UUID customerId, String currency) {
        this.accountId = UUID.randomUUID();
        this.customerId = customerId;
        this.balance = BigDecimal.ZERO;
        this.currency = currency;
        this.status = AccountStatus.ACTIVE;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void debit(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
