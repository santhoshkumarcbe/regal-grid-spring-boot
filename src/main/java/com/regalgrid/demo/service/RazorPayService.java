package com.regalgrid.demo.service;

import com.regalgrid.demo.model.TransactionDetails;

public interface RazorPayService {

    TransactionDetails createTransaction(double amount);
    
}
