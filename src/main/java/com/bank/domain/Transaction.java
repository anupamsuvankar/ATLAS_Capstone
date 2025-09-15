package com.bank.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bank.enums.TransactionStatus;

public class Transaction {
    private UUID transactionId;
    private String fromAccountId;
    private String toAccountId;
    private int amount;
    private String currency;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String idempotencyKey;

    public Transaction(String from, String to, int amount, String currency) {
        this.transactionId = UUID.randomUUID();
        this.fromAccountId = from;
        this.toAccountId = to;
        this.amount = amount;
        this.currency = currency;
        this.status = TransactionStatus.PENDING;
        this.timestamp = LocalDateTime.now();
        this.idempotencyKey = UUID.randomUUID().toString();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
