package com.bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {
    private UUID logId;
    private UUID transactionId;
    private String actor;
    private String action;
    private BigDecimal beforeBalance;
    private BigDecimal afterBalance;
    private LocalDateTime timestamp;

    public AuditLog(UUID transactionId, String actor, String action, 
                    BigDecimal beforeBalance, BigDecimal afterBalance) {
        this.logId = UUID.randomUUID();
        this.transactionId = transactionId;
        this.actor = actor;
        this.action = action;
        this.beforeBalance = beforeBalance;
        this.afterBalance = afterBalance;
        this.timestamp = LocalDateTime.now();
    }

    public String toString() {
        return String.format("[%s] %s performed %s | Before: %s After: %s", 
                timestamp, actor, action, beforeBalance, afterBalance);
    }
}
