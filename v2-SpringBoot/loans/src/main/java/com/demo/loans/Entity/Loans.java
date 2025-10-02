package com.demo.loans.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Entity @Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @NotEmpty
    private String mobileNumber;

    @NotEmpty
    private String loanNumber;

    @NotEmpty
    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;

}