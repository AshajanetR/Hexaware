package com.java.LoanManagement.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Loan 
{
    private int loanId;
    private Customer customer;
    private double principalAmount;
    private double interestRate;
    private int loanTerm;
    private loanType loanType;
    private loanStatus loanStatus;
    
    @Override
    public String toString() {
        return "Loan(loanId=" + loanId + ", customer=" + customer +
               ", principalAmount=" + principalAmount +
               ", interestRate=" + interestRate +
               ", loanTerm=" + loanTerm +
               ", loanType=" + loanType +
               ", loanStatus=" + loanStatus + ")";
    }


}
