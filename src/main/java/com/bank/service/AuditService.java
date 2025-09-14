package com.bank.service;

import com.bank.domain.AuditLog;
import com.bank.domain.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AuditService {
    private List<AuditLog> logs = new ArrayList<>();

    public void log(Transaction tx, String actor, String action,
                    BigDecimal before, BigDecimal after) {
        AuditLog log = new AuditLog(tx.getTransactionId(), actor, action, before, after);
        logs.add(log);
        System.out.println("AUDIT: " + log);
    }

    public List<AuditLog> getLogs() {
        return logs;
    }
}
