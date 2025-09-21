package com.demo.loans.dto;

import lombok.Data;

@Data
public class LoansDto {

    private int loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;

}
