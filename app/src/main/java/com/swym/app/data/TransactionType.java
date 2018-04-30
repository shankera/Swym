package com.swym.app.data;

public enum TransactionType {
    WITHDRAWAL("Withdrawal"),
    DEPOSIT("Deposit");

    private final String type;

    TransactionType(String s) {
        type = s;
    }

    public String toString() {
        return this.type;
    }
}
