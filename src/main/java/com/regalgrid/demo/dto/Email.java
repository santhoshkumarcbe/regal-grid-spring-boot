package com.regalgrid.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Email {
    private String toEmail;
    private String subject;
    private String body;
}