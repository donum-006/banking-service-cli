package com.kena.banking.model;

import java.util.ArrayList;

import com.kena.banking.exception.InsufficientFundsException;

public class Account {
    private String name;
    private String  accountNumber;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.transactions = new ArrayList<>();
        if (balance < 0) this.balance = 0;
        else this.balance = balance;
    }
    // getters
    public String getName() {
        return this.name;
    }
    public String  getAccountNumber() {
        return this.accountNumber;
    }
    public double getBalance() {
        return this.balance;
    }
    public ArrayList<Transaction> getTransactions() { 
        return this.transactions; 
    }
    // setters
    public void credit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        this.balance += amount;
        
    }
    public void debit(double amount) throws InsufficientFundsException{
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero");
        }
        if (this.balance < amount) {
            throw new InsufficientFundsException(String.format("Transaction Denied: Account %s has a balance of $%s, which is insufficient for a withdrawal of $%s.",
             this.accountNumber,this.balance,amount));
        }
        this.balance -= amount;
        

    }
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    public void printTransaction() {
        if (this.transactions.isEmpty()) {
            System.out.println("No transaction history exist.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
    @Override
    public String toString() {
        return String.format("Name: %s | Account Number: %s | Current Balance: %s",name,accountNumber,balance);
    }

}

