package com.java.LoanManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.LoanManagement.exception.InvalidLoanException;
import com.java.LoanManagement.model.Loan;
import com.java.LoanManagement.model.LoanStatus;
import com.java.LoanManagement.model.LoanType;
import com.java.LoanManagement.util.ConnectionHelper;

public class ILoanRepositoryImpl implements ILoanRepository{
    
	Connection connection;
	PreparedStatement pst;
	
	static Scanner sc;
	static{
		sc = new Scanner(System.in);
	}
	
	@Override
	public void applyLoan(Loan loan) throws ClassNotFoundException, SQLException {
	    
	    double interest = calculateInterest(
	        loan.getPrincipalAmount(),
	        loan.getInterestRate(),
	        loan.getLoanTerm()
	    );
	    double emi = calculateEMI(loan.getPrincipalAmount(),loan.getInterestRate(),loan.getLoanTerm());
	    
	    System.out.printf("Estimated interest amount for this loan: ₹%.2f%n", interest);
        System.out.printf("Estimated emi for this loan: ₹%.2f%n",emi);
	    
	    System.out.print("Do you want to apply for the loan? (Yes/No): ");
	    String confirmation = sc.nextLine();

	    if (confirmation.equalsIgnoreCase("No")) {
	        System.out.println("Loan application cancelled.");
	        return;
	    }

	    
	    loan.setLoanStatus(LoanStatus.Pending);

	   
	    connection = ConnectionHelper.getConnection();
	    String cmd = "INSERT INTO Loan (loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus, " +
	                 "propertyAddress, propertyValue, carModel, carValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    pst = connection.prepareStatement(cmd);
	    pst.setInt(1, loan.getLoanId());
	    pst.setInt(2, loan.getCustomerId());
	    pst.setDouble(3, loan.getPrincipalAmount());
	    pst.setDouble(4, loan.getInterestRate());
	    pst.setInt(5, loan.getLoanTerm());
	    pst.setString(6, loan.getLoanType().toString());
	    pst.setString(7, loan.getLoanStatus().toString());
	    pst.setString(8, loan.getPropertyAddress());
	    
	    if (loan.getPropertyValue() != null)
	        pst.setInt(9, loan.getPropertyValue());
	    else
	        pst.setNull(9, java.sql.Types.INTEGER);

	    pst.setString(10, loan.getCarModel());
	    
	    if (loan.getCarValue() != null)
	        pst.setInt(11, loan.getCarValue());
	    else
	        pst.setNull(11, java.sql.Types.INTEGER);

	    int res = pst.executeUpdate();
	    if (res > 0)
	        System.out.println("Loan applied successfully with status PENDING.");
	    else
	        System.out.println("Loan application failed.");
	}

	
	
	@Override
	public double calculateInterest(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();
	    
	    String query = "SELECT principalAmount, interestRate, loanTerm FROM Loan WHERE loanId = ?";
	    pst = connection.prepareStatement(query);
	    pst.setInt(1, loanId);

	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
	        double principal = rs.getDouble("principalAmount");
	        double rate = rs.getDouble("interestRate");
	        int term = rs.getInt("loanTerm");

	        double interest = (principal * rate * term) / 12 / 100;
	        System.out.println("Calculated Interest: " + interest);
	        return interest;

	    } else {
	        throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	    }
	}
	
	@Override
	public double calculateInterest(double principal, double rate, int term) {
	    double interest = (principal * rate * term) / 12 / 100;
//	    System.out.println("Calculated Interest (overloaded): " + interest);
	    return interest;
	}

	@Override
	public void loanStatus(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();

	    
	    String query = "SELECT c.creditScore FROM Loan l " +
	                   "JOIN Customer c ON l.customerId = c.customerId " +
	                   "WHERE l.loanId = ?";
	    
	    pst = connection.prepareStatement(query);
	    pst.setInt(1, loanId);
	    ResultSet rs = pst.executeQuery();

	    if (!rs.next()) {
	    	throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	    }

	    int creditScore = rs.getInt("creditScore");

	   
	    LoanStatus newStatus = (creditScore > 650) ? LoanStatus.Approved : LoanStatus.Rejected;

	    
	    String update = "UPDATE Loan SET loanStatus = ? WHERE loanId = ?";
	    pst = connection.prepareStatement(update);
	    pst.setString(1, newStatus.toString());
	    pst.setInt(2, loanId);

	    int res = pst.executeUpdate();
	    if (res > 0) {
	        System.out.println("Loan status updated successfully.");
	        System.out.println("Loan ID " + loanId + " is " + newStatus.toString().toUpperCase());
	    } else {
	        System.out.println("Failed to update loan status.");
	    }
	}
	
	@Override
	public double calculateEMI(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();

	    String query = "SELECT principalAmount, interestRate, loanTerm FROM Loan WHERE loanId = ?";
	    pst = connection.prepareStatement(query);
	    pst.setInt(1, loanId);
	    ResultSet rs = pst.executeQuery();

	    if (!rs.next()) {
	        throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	    }

	    double principal = rs.getDouble("principalAmount");
	    double annualRate = rs.getDouble("interestRate");
	    int months = rs.getInt("loanTerm");

	    return calculateEMI(principal, annualRate, months); 
	}
    
	@Override
	public double calculateEMI(double principalAmount, double annualInterestRate, int loanTermInMonths) {
	    double monthlyRate = annualInterestRate / 12 / 100; 

	    double numerator = principalAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTermInMonths);
	    double denominator = Math.pow(1 + monthlyRate, loanTermInMonths) - 1;

	    if (denominator == 0) {
	        return principalAmount / loanTermInMonths; 
	    }

	    double emi = numerator / denominator;
	    return Math.round(emi * 100.0) / 100.0; 
	}
	
	@Override
	public void loanRepayment(int loanId, double amount) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();

	    
	    String query = "SELECT principalAmount, interestRate, loanTerm FROM Loan WHERE loanId = ?";
	    pst = connection.prepareStatement(query);
	    pst.setInt(1, loanId);
	    ResultSet rs = pst.executeQuery();

	    if (!rs.next()) {
	        throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	    }

	    double principal = rs.getDouble("principalAmount");
	    double interestRate = rs.getDouble("interestRate");
	    int term = rs.getInt("loanTerm");

	  
	    double emi = calculateEMI(principal, interestRate, term);

	   
	    if (amount < emi) {
	        System.out.printf("Payment rejected. ₹%.2f is less than a single EMI of ₹%.2f%n", amount, emi);
	        return;
	    }

	   
	    int emisPaid = (int)(amount / emi);
	    double leftover = amount - (emisPaid * emi);

	    System.out.println("Payment successful!");
	    System.out.println("Loan ID: " + loanId);
	    System.out.printf("Amount Paid: ₹%.2f%n", amount);
	    System.out.println("Full EMIs Paid: " + emisPaid);
	    System.out.printf("Remaining Amount in Wallet: ₹%.2f%n", leftover);

	   
	}
	
	@Override
	public List<Loan> getAllLoan() throws ClassNotFoundException, SQLException {
	    connection = ConnectionHelper.getConnection();

	    String query = "SELECT * FROM Loan";
	    pst = connection.prepareStatement(query);
	    ResultSet rs = pst.executeQuery();
        
	    Loan l = null;
	    List <Loan> loanlist = new ArrayList<>();
	    while(rs.next()) {
	    	l = new Loan();
	    	l.setLoanId(rs.getInt("loanId"));
	    	l.setCustomerId(rs.getInt("customerId"));
	    	l.setPrincipalAmount(rs.getDouble("principalAmount"));
	    	l.setInterestRate(rs.getDouble("interestRate"));
	    	l.setLoanTerm(rs.getInt("loanTerm"));
	    	l.setLoanType(LoanType.valueOf(rs.getString("loanType")));
	    	l.setLoanStatus(LoanStatus.valueOf(rs.getString("loanStatus")));
	    	l.setPropertyAddress(rs.getString("propertyAddress"));
	    	l.setPropertyValue(rs.getInt("propertyValue"));
	    	l.setCarModel(rs.getString("carModel"));
	    	l.setCarValue(rs.getInt("carValue"));
	    	
	    	loanlist.add(l);
	    }
	    rs.close();
	    pst.close();
	    connection.close();
	    return loanlist;
	}
    
	@Override
	public void getLoanById(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();

	    String query = "SELECT * FROM Loan WHERE loanId = ?";
	    pst = connection.prepareStatement(query);
	    pst.setInt(1, loanId);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
	        System.out.println("Loan ID: " + rs.getInt("loanId"));
	        System.out.println("Customer ID: " + rs.getInt("customerId"));
	        System.out.println("Principal Amount: " + rs.getDouble("principalAmount"));
	        System.out.println("Interest Rate: " + rs.getDouble("interestRate"));
	        System.out.println("Loan Term: " + rs.getInt("loanTerm"));
	        System.out.println("Loan Type: " + rs.getString("loanType"));
	        System.out.println("Loan Status: " + rs.getString("loanStatus"));

	        String propertyAddress = rs.getString("propertyAddress");
	        if (propertyAddress != null) {
	            System.out.println("Property Address: " + propertyAddress);
	            System.out.println("Property Value: " + rs.getInt("propertyValue"));
	        }

	        String carModel = rs.getString("carModel");
	        if (carModel != null) {
	            System.out.println("Car Model: " + carModel);
	            System.out.println("Car Value: " + rs.getInt("carValue"));
	        }

	    } else {
	        throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	    }

	    rs.close();
	    pst.close();
	    connection.close();
	}
    

}
