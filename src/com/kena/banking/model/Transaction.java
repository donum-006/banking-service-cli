package com.kena.banking.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionID;
    private String transactionType;
    private LocalDateTime time;
    private double amount;

    public Transaction(String transactionID, String transactionType, LocalDateTime time, double amount) {
        this.transactionID = transactionID; // Takes the original saved ID directly!
        this.transactionType = transactionType;
        this.time = time;
        this.amount = amount;
    }
     public Transaction(String transactionType,LocalDateTime time,double amount) {
       this(java.util.UUID.randomUUID().toString().substring(0,8).toUpperCase(),transactionType,time,amount);
    }
    // getters
    public String getTransactionID() {
        return this.transactionID;
    }
    public String getTransactionType() {
        return this.transactionType;
    }
    public LocalDateTime getTime() {
        return this.time;
    }
    public double getAmount() {
        return this.amount;
    }
    @Override
    public String toString() {
        return String.format("[Transaction Id] %s | Transaction Type : %s | Amount : %s | Time : %s",transactionID,transactionType,amount,time);

    }
}
