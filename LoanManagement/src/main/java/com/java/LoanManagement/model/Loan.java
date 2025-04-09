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
@ToString

public class Loan {
	private int loanId;
    private int customerId;
    private double principalAmount;
    private double interestRate;
    private int loanTerm;
    private LoanType loanType;
    private LoanStatus loanStatus;

    
    private String propertyAddress;
    private Integer propertyValue;

    
    private String carModel;
    private Integer carValue;
}
