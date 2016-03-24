package com.swym.app.data;

public enum TransactionType {
    TRANSACTION ("Transaction"),
    WITHDRAWAL ("Withdrawal"),
    DEPOSIT ("Deposit");

    private final String type;

    TransactionType(String s) {
        type = s;
    }

    public String toString() {
        return this.type;
    }
}
