package com.demo.loans.Service;

import com.demo.loans.dto.LoansDto;

public interface iLoansService {

    void createLoan(LoansDto loansDto);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);

}
