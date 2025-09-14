package com.bank.domain;

import com.bank.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String idempotencyKey;

    public Transaction(UUID from, UUID to, BigDecimal amount, String currency) {
        this.transactionId = UUID.randomUUID();
        this.fromAccountId = from;
        this.toAccountId = to;
        this.amount = amount;
        this.currency = currency;
        this.status = TransactionStatus.PENDING;
        this.timestamp = LocalDateTime.now();
        this.idempotencyKey = UUID.randomUUID().toString();
    }

    public UUID getTransactionId() { return transactionId; }
    public UUID getFromAccountId() { return fromAccountId; }
    public UUID getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getIdempotencyKey() { return idempotencyKey; }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
