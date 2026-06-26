package com.kena.banking;

import com.kena.banking.service.BankService;
import com.kena.banking.ui.BankUI;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        
        BankUI ui = new BankUI(bankService);
        
        
        ui.start();
    }
}