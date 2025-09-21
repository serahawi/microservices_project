package com.demo.loans.Service.Impl;

import com.demo.loans.Constants.LoansConstants;
import com.demo.loans.Entity.Loans;
import com.demo.loans.Exception.AlreadyExistsException;
import com.demo.loans.Exception.ResourceNotFoundException;
import com.demo.loans.Mapper.LoansMapper;
import com.demo.loans.Repository.LoansRepository;
import com.demo.loans.Service.iLoansService;
import com.demo.loans.dto.LoansDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements iLoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> existLoan = loansRepository.findByMobileNumber(mobileNumber);
        if (existLoan.isPresent()) {
            throw new AlreadyExistsException(mobileNumber);
        }
        Loans loan = generateLoan(mobileNumber);
        loansRepository.save(loan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans ", "MobileNumber" , mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loans","LoanNumber",loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        boolean isDeleted = false;
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans ", "MobileNumber",String.valueOf(mobileNumber))
        );

        loansRepository.delete(loans);
        isDeleted = true;

        return isDeleted;
    }

    private Loans generateLoan(String mobileNumber) {
        Loans loans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }

}
