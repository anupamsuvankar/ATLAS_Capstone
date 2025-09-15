package com.bank.domain;

import com.bank.enums.KycStatus;
import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private KycStatus kycStatus;

    public Customer(String name, String email, KycStatus kycStatus) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.kycStatus = kycStatus;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }
}
