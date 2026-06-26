package com.kena.banking.service;

import com.kena.banking.exception.AccountNotFoundException;
import com.kena.banking.exception.InsufficientFundsException;
import com.kena.banking.exception.AccountExistException;

import java.time.LocalDateTime;
import com.kena.banking.model.*;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;


import java.util.HashMap;

public class BankService {
    private Map<String,Account> accounts;
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public BankService() {
        accounts = new HashMap<>();
    }

    public void createAccount(String name, String accountNumber, double initialBalance) throws AccountExistException {
        if (accountExist(accountNumber)) {
            throw new AccountExistException(String.format("Account %s already exist!",accountNumber));
        }
        Account userAccount = new Account(name, accountNumber, initialBalance);
        addAccount(userAccount);
        saveData();

    }
    public Account findAccount(String accountNumber) throws AccountNotFoundException{
        if (!(accountExist(accountNumber))) {
            throw new AccountNotFoundException("Error: Account number " + accountNumber + " does not exist.");
        }
        return accounts.get(accountNumber);
    }
    public void performDeposit(String accountNumber, double amount) throws AccountNotFoundException{
        Account account = findAccount(accountNumber);
        account.credit(amount);
        Transaction transaction = new Transaction("Diposite: " + accountNumber, LocalDateTime.now(), amount);
        account.addTransaction(transaction);
        System.out.println("Deposit of $" + amount + " successfully processed for account " + accountNumber);
        saveData();
    }
    public void performWithdrawal(String accountNumber, double amount) throws AccountNotFoundException,InsufficientFundsException {
        Account account = findAccount(accountNumber);
        account.debit(amount);
        Transaction transaction = new Transaction("Withdrawal " + accountNumber, LocalDateTime.now(), amount);
        account.addTransaction(transaction);
        System.out.println("Withdrawal of $" + amount + " successfully processed for account " + accountNumber);
        saveData();
    }
    public void performTransfer(String senderAcc, String receiverAcc, double amount) throws AccountNotFoundException, InsufficientFundsException {
        if (senderAcc.equals(receiverAcc)) {
            throw new IllegalArgumentException("Sender account and receiver cannot be the same.");
        }
        Account sendAccount = findAccount(senderAcc);
        Account receiveAccount = findAccount(receiverAcc);
        sendAccount.debit(amount);
        receiveAccount.credit(amount);
        sendAccount.addTransaction(new Transaction("TRANSFER_OUT to "  + receiverAcc, LocalDateTime.now(), amount));
        receiveAccount.addTransaction(new Transaction("TRANSFER_IN from " + senderAcc, LocalDateTime.now(), amount));
        System.out.printf("Success: Transferred $%.2f from account %s to %s.%n", amount, sendAccount, receiveAccount);
        saveData();
    }
    public void saveData() {
        try (BufferedWriter acountWriter = new BufferedWriter(new FileWriter(ACCOUNTS_FILE));
            BufferedWriter transactionWriter = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE)) ) {
            for (Account account : accounts.values()) {
                acountWriter.write(String.format("%s,%s,%.2f", account.getName(),account.getAccountNumber(),account.getBalance()));
                acountWriter.newLine();
                for (Transaction tx : account.getTransactions()) {
                    transactionWriter.write(String.format("%s,%s,%s,%.2f,%s", 
                    account.getAccountNumber(),
                    tx.getTransactionID(),
                    tx.getTransactionType(),
                    tx.getAmount(),
                    tx.getTime().toString()));
                    transactionWriter.newLine();
                }
            }
            System.out.println("Data successfully saved to disk.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            
        }
    }
    public void loadData() {
    File accFile = new File(ACCOUNTS_FILE);
    File txFile = new File(TRANSACTIONS_FILE);
    if (!accFile.exists() || !txFile.exists()) {
        System.out.println("No saved session data found. Starting fresh!");
        return;
    }
    accounts.clear();
    try (BufferedReader accountReader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
        String line;
        while ((line = accountReader.readLine()) != null) {
            // Raw text layout from file: Name,AccountNumber,Balance
            String[] tokens = line.split(",");
            if (tokens.length == 3) {
                String name = tokens[0];
                String accNum = tokens[1];
                double balance = Double.parseDouble(tokens[2]);


                Account account = new Account(name, accNum, balance);
                accounts.put(accNum, account);
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.err.println("Error reading accounts file: " + e.getMessage());
    }

    try (BufferedReader txReader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
        String line;
        while ((line = txReader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length == 5) {
                String accNum = tokens[0];
                String txID = tokens[1];
                String type = tokens[2];
                double amount = Double.parseDouble(tokens[3]);
                LocalDateTime timestamp = LocalDateTime.parse(tokens[4]);

                try {
                    Account account = findAccount(accNum);
                    Transaction tx = new Transaction(txID, type, timestamp, amount);
                
                    account.addTransaction(tx);
                    
                } catch (AccountNotFoundException e) {
                    System.err.println("Warning: Found transaction for missing account: " + accNum);
                }
            }
        }
        System.out.println("Session data loaded successfully.");
    } catch (IOException | NumberFormatException e) {
        System.err.println("Error reading transactions file: " + e.getMessage());
    }
}
    // helper methods
    public boolean accountExist(String accountNumber) {
        return this.accounts.containsKey(accountNumber);
    }
    public void addAccount(Account account) {
        this.accounts.put(account.getAccountNumber(), account);
    }
         
} 
