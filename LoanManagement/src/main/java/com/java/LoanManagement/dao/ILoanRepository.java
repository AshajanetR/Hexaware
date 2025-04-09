package com.java.LoanManagement.dao;

import java.sql.SQLException;

import com.java.LoanManagement.exception.InvalidLoanException;
import com.java.LoanManagement.model.Loan;

public interface ILoanRepository {
	void applyLoan(Loan loan) throws ClassNotFoundException, SQLException;

	double calculateInterest(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException, InvalidLoanException;

	double calculateInterest(double principal, double rate, int term);

	void loanStatus(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException;

	double calculateEMI(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException;

	double calculateEMI(double principalAmount, double annualInterestRate, int loanTermInMonths);

	void loanRepayment(int loanId, double amount) throws ClassNotFoundException, SQLException, InvalidLoanException;

	void getAllLoan() throws ClassNotFoundException, SQLException;

	void getLoanById(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException;
}
