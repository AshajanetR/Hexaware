package com.java.LoanManagement.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.LoanManagement.exception.InvalidLoanException;
import com.java.LoanManagement.model.CarLoan;
import com.java.LoanManagement.model.Customer;
import com.java.LoanManagement.model.HomeLoan;
import com.java.LoanManagement.model.Loan;
import com.java.LoanManagement.model.loanType;
import com.java.LoanManagement.util.ConnectionHelper;

 

public class ILoanRepositoryImpl implements ILoanRepository {
	
	Connection connection;
	PreparedStatement pst;
	 
		@SuppressWarnings("resource")
		@Override
		public void applyLoan(Loan loan) throws ClassNotFoundException, SQLException {
		    connection = ConnectionHelper.getConnection();
		    Scanner sc = new Scanner(System.in);  
		    double interest = calculateInterest(
		        loan.getPrincipalAmount(),
		        loan.getInterestRate(),
		        loan.getLoanTerm()
		    );
		    System.out.printf("Estimated interest amount for this loan: ₹%.2f%n", interest);
		    System.out.print("Do you want to apply for the loan? (Yes/No): ");
		    String confirm = sc.nextLine();
		    if (!confirm.equalsIgnoreCase("Yes")) 
		    {
		        System.out.println("Loan application cancelled.");
		        return;
		    }
		        loan.setLoanStatus(com.java.LoanManagement.model.loanStatus.Pending); 
		        String cmd = "INSERT INTO Loan (customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus, " +
		                     "propertyAddress, propertyValue, carModel, carValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        pst = connection.prepareStatement(cmd);
		        pst.setInt(1, loan.getCustomer().getCustomerId());
		        pst.setDouble(2, loan.getPrincipalAmount());
		        pst.setDouble(3, loan.getInterestRate());
		        pst.setInt(4, loan.getLoanTerm());
		        pst.setString(5, loan.getLoanType().toString());
		        pst.setString(6, loan.getLoanStatus().toString());
		        
		        if (loan instanceof HomeLoan homeLoan) {
		            pst.setString(7, homeLoan.getPropertyAddress());
		            pst.setInt(8, homeLoan.getPropertyValue());
		            pst.setNull(9, java.sql.Types.VARCHAR);
		            pst.setNull(10, java.sql.Types.INTEGER);
		        } else if (loan instanceof CarLoan carLoan) {
		            pst.setNull(7, java.sql.Types.VARCHAR);
		            pst.setNull(8, java.sql.Types.INTEGER);
		            pst.setString(9, carLoan.getCarModel());
		            pst.setInt(10, carLoan.getCarValue());
		        } else {
		            pst.setNull(7, java.sql.Types.VARCHAR);
		            pst.setNull(8, java.sql.Types.INTEGER);
		            pst.setNull(9, java.sql.Types.VARCHAR);
		            pst.setNull(10, java.sql.Types.INTEGER);
		        }
		        
		        int rows = pst.executeUpdate();
		        if (rows > 0) System.out.println("Loan successfully applied!");
		        else System.out.println("Error applying loan: ");
//		        sc.close();
		}
		
		@Override
		public double calculateInterest(int loanId) throws InvalidLoanException, ClassNotFoundException, SQLException {
			connection = ConnectionHelper.getConnection();
			String cmd = "SELECT principalAmount, interestRate, loanTerm FROM Loan WHERE loanId = ?";
	        pst=connection.prepareStatement(cmd);
			pst.setInt(1,loanId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double p = rs.getDouble("principalAmount");
                double r = rs.getDouble("interestRate");
                int n = rs.getInt("loanTerm");
                return calculateInterest(p, r, n);
            }else {
            	 throw new InvalidLoanException("Loan ID not found for interest calculation.");
            }
        }
            	
		  @Override
		    public double calculateInterest(double principal, double rate, int tenureMonths) {
		        return (principal * rate * tenureMonths) / (12 * 100);
		    }

	@Override
	public String loanStatus(int loanId) throws InvalidLoanException, ClassNotFoundException, SQLException {
		connection = ConnectionHelper.getConnection();
        String cmd = "SELECT c.creditScore, l.loanStatus FROM Customer c JOIN Loan l ON c.customerId = l.customerId WHERE l.loanId = ?";
        pst=connection.prepareStatement(cmd);
        pst.setInt(1,loanId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) 
        {
            int score = rs.getInt("creditScore");
            String status = (score > 650) ? "Approved" : "Rejected";
            PreparedStatement updatepst= connection.prepareStatement("UPDATE Loan SET loanStatus=? WHERE loanId=?");
            updatepst.setString(1, status.toString());
            updatepst.setInt(2, loanId);
            updatepst.executeUpdate();  
            return status;
        } else {
            throw new InvalidLoanException("Loan ID not found for status update.");
        }
	}
	
	@Override
    public double calculateEMI(int loanId) throws InvalidLoanException, SQLException, ClassNotFoundException {
		connection = ConnectionHelper.getConnection();
        String cmd="SELECT principalAmount, interestRate, loanTerm FROM Loan WHERE loanId = ?";
        pst=connection.prepareStatement(cmd);
        pst.setInt(1, loanId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            double p = rs.getDouble("principalAmount");
            double r = rs.getDouble("interestRate") / 12 / 100;
            int n = rs.getInt("loanTerm");
            return calculateEMI(p, r, n);
        } else {
            throw new InvalidLoanException("Loan ID not found for EMI calculation.");
        }
    }

    @Override
    public double calculateEMI(double principal, double monthlyRate, int months) {
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
               (Math.pow(1 + monthlyRate, months) - 1);
    }

    @Override
    public void loanRepayment(int loanId, double amount) throws InvalidLoanException, ClassNotFoundException, Exception {
        double emi = calculateEMI(loanId);
        if (amount < emi) {
            System.out.println("Payment rejected. Amount is less than EMI.");
        } else {
            int emiCount = (int) (amount / emi);
            System.out.println("Payment accepted. Number of EMIs paid: " + emiCount);
        }
    }

    
  
    @Override
    public List<Loan> getAllLoan() throws SQLException, ClassNotFoundException {
        List<Loan> loans = new ArrayList<>();
        connection = ConnectionHelper.getConnection();
        String cmd = "SELECT * FROM Loan";
        pst = connection.prepareStatement(cmd);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            loanType type = loanType.valueOf(rs.getString("loanType"));
            Loan loan;

            if (type == loanType.CarLoan) {
                CarLoan carLoan = new CarLoan();
                carLoan.setCarModel(rs.getString("carModel"));
                carLoan.setCarValue(rs.getInt("carValue"));
                loan = carLoan;
            } else if (type == loanType.HomeLoan) {
                HomeLoan homeLoan = new HomeLoan();
                homeLoan.setPropertyAddress(rs.getString("propertyAddress"));
                homeLoan.setPropertyValue(rs.getInt("propertyValue"));
                loan = homeLoan;
            } else {
                loan = new Loan(); 
            }
          
            loan.setLoanId(rs.getInt("loanId"));
            loan.setPrincipalAmount(rs.getDouble("principalAmount"));
            loan.setInterestRate(rs.getDouble("interestRate"));
            loan.setLoanTerm(rs.getInt("loanTerm"));
            loan.setLoanType(type);
            loan.setLoanStatus(com.java.LoanManagement.model.loanStatus.valueOf(rs.getString("loanStatus")));

            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("customerId"));
            loan.setCustomer(customer);

            loans.add(loan);
        }

        return loans;
    }

	
	
	@Override
	public Loan getLoanById(int loanId) throws ClassNotFoundException, SQLException, InvalidLoanException {
	    connection = ConnectionHelper.getConnection();
	    String cmd = "SELECT * FROM Loan WHERE loanId = ?";
	    pst = connection.prepareStatement(cmd);
	    pst.setInt(1, loanId);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
	        loanType type = loanType.valueOf(rs.getString("loanType"));
	        Loan loan;

	        if (type == loanType.CarLoan) {
	            CarLoan carLoan = new CarLoan();
	            carLoan.setCarModel(rs.getString("carModel"));
	            carLoan.setCarValue(rs.getInt("carValue"));
	            loan = carLoan;
	        } else if (type == loanType.HomeLoan) {
	            HomeLoan homeLoan = new HomeLoan();
	            homeLoan.setPropertyAddress(rs.getString("propertyAddress"));
	            homeLoan.setPropertyValue(rs.getInt("propertyValue"));
	            loan = homeLoan;
	        } else {
	            loan = new Loan(); 
	        }

	        loan.setLoanId(rs.getInt("loanId"));
	        loan.setPrincipalAmount(rs.getDouble("principalAmount"));
	        loan.setInterestRate(rs.getDouble("interestRate"));
	        loan.setLoanTerm(rs.getInt("loanTerm"));
	        loan.setLoanType(type);
	        loan.setLoanStatus(com.java.LoanManagement.model.loanStatus.valueOf(rs.getString("loanStatus")));

	        Customer customer = new Customer();
	        customer.setCustomerId(rs.getInt("customerId"));
	        loan.setCustomer(customer);

	        return loan;
	    } else {
	        throw new InvalidLoanException("Loan ID not found.");
	    }
	}



    
    

}