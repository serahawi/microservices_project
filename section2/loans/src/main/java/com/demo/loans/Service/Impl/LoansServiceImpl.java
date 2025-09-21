package com.demo.loans.Service.Impl;

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

@Service
@AllArgsConstructor
public class LoansServiceImpl implements iLoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(LoansDto loansDto) {
        Optional<Loans> existLoan = loansRepository.findByMobileNumber(loansDto.getMobileNumber());
        if (existLoan.isPresent()) {
            throw new AlreadyExistsException(loansDto.getMobileNumber());
        }
        Loans loan = LoansMapper.mapToLoans(loansDto,new Loans());
        loansRepository.save(loan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = checkLoanExistanceWithMobileNumber(mobileNumber);
        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        Loans existLoan = loansRepository.findById(loansDto.getLoanId()).orElseThrow(
                () -> new ResourceNotFoundException("Loans","LoanId",String.valueOf(loansDto.getLoanId()))
        );

        Loans newLoan = LoansMapper.mapToLoans(loansDto,new Loans());
        newLoan.setLoanId(existLoan.getLoanId());

        loansRepository.save(newLoan);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        return false;
    }

    public Loans checkLoanExistanceWithMobileNumber(String mobileNumber) {
        return loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans ", "MobileNumber" , mobileNumber)
        );
    }
}
