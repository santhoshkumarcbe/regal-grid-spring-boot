package com.regalgrid.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "Transaction_Details")
public class TransactionDetails {
    @Id
    private String id;
    private String orderId;
    private String currency;
    private Integer amount;
    private String key;
}
