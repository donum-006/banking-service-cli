package com.kena.banking.ui;

import com.kena.banking.service.BankService;
import com.kena.banking.exception.AccountExistException;
import com.kena.banking.exception.AccountNotFoundException;
import com.kena.banking.exception.InsufficientFundsException;
import java.util.Scanner;

public class BankUI {
    private final BankService bankService;
    private final Scanner scanner;

    public BankUI(BankService bankService) {
        this.bankService = bankService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        bankService.loadData();

        System.out.println("======================================");
        System.out.println("           BANKING SERVICE            ");
        System.out.println("======================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Display Account Details");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Funds");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline buffer

            switch (choice) {
                case 1: handleCreateAccount(); break;
                case 2: handleDisplayAccount(); break;
                case 3: handleDeposit(); break;
                case 4: handleWithdrawal(); break;
                case 5: handleTransfer(); break;
                case 6: handleViewTransactions(); break;
                case 7:
                    System.out.println("Thank you for using our Banking Service. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 7.");
            }
        }
    }

    private void handleCreateAccount() {
        System.out.println("\n--- Open New Account ---");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Initial Deposit: ");
        if (!scanner.hasNextDouble()) { System.out.println("Invalid amount."); scanner.nextLine(); return; }
        double deposit = scanner.nextDouble(); scanner.nextLine();

        try {
            bankService.createAccount(name, accNum, deposit);
            System.out.println("Success: Account created and saved securely.");
        } catch (AccountExistException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private void handleDisplayAccount() {
        System.out.println("\n--- View Account Details ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();

        try {
            var account = bankService.findAccount(accNum);
            System.out.println("\n" + account);
        } catch (AccountNotFoundException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private void handleDeposit() {
        System.out.println("\n--- Deposit Funds ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Deposit Amount: ");
        if (!scanner.hasNextDouble()) { System.out.println("Invalid amount."); scanner.nextLine(); return; }
        double amount = scanner.nextDouble(); scanner.nextLine();

        try {
            bankService.performDeposit(accNum, amount);
        } catch (AccountNotFoundException | IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private void handleWithdrawal() {
        System.out.println("\n--- Withdraw Funds ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Withdrawal Amount: ");
        if (!scanner.hasNextDouble()) { System.out.println("Invalid amount."); scanner.nextLine(); return; }
        double amount = scanner.nextDouble(); scanner.nextLine();

        try {
            bankService.performWithdrawal(accNum, amount);
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleTransfer() {
        System.out.println("\n--- Fund Transfer ---");
        System.out.print("Enter Sender Account Number: ");
        String sender = scanner.nextLine();
        System.out.print("Enter Recipient Account Number: ");
        String recipient = scanner.nextLine();
        System.out.print("Enter Transfer Amount: ");
        if (!scanner.hasNextDouble()) { System.out.println("Invalid amount."); scanner.nextLine(); return; }
        double amount = scanner.nextDouble(); scanner.nextLine();

        try {
            bankService.performTransfer(sender, recipient, amount);
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleViewTransactions() {
        System.out.println("\n--- Transaction History ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();

        try {
            var account = bankService.findAccount(accNum);
            System.out.println("\nHistory for Account: " + accNum);
            account.printTransaction();
        } catch (AccountNotFoundException e) { System.out.println("Error: " + e.getMessage()); }
    }
}