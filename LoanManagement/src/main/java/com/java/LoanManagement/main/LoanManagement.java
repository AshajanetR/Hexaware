package com.java.LoanManagement.main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.java.LoanManagement.dao.ILoanRepository;
import com.java.LoanManagement.dao.ILoanRepositoryImpl;
import com.java.LoanManagement.exception.InvalidLoanException;
import com.java.LoanManagement.model.Loan;
import com.java.LoanManagement.model.LoanType;

public class LoanManagement {
    
	static Scanner sc;
	static ILoanRepository iloan;
	
	static{
		sc = new Scanner(System.in);
		iloan = new ILoanRepositoryImpl();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		int choice;
		
		do {
			
			 System.out.println("\n--------------------------------------\n");
	    	  System.out.println("O P T I O N S");
	    	  System.out.println("-------------");
	    	  System.out.println("1. Apply Loan");
	          System.out.println("2. Get All Loans");
	          System.out.println("3. Get Loan by ID");
	          System.out.println("4. Loan Repayment");
	          System.out.println("5  Check Loan Status");
	          System.out.println("6. Check EMI");
	          System.out.println("7. Check Interest");
	          System.out.println("8. Exit");
	          System.out.print("Enter your choice: ");
	          choice = sc.nextInt();
	          
	          switch(choice) {
	          case 1:
	        	  ApplyLoanMain();
	        	  break;
	          case 2:
	        	  getAllLoanMain();
	        	  break;
	          case 3:
	        	  getLoanMain();
	        	  break;
	          case 4:
	        	  loanRepayment();
	        	  break;
	          case 5:
	        	  LoanStatusMain();
	        	  break;
	          case 6:
	        	  CHeckEMI();
	        	  break;
	          case 7:
	        	  CHeckInterest();
	        	  break;
	          }
		}while(choice!=8);
		
	}
	
	public static void ApplyLoanMain() {
	    Loan loan = new Loan();

	    System.out.print("Enter Customer ID: ");
	    loan.setCustomerId(sc.nextInt());
	    sc.nextLine(); 

	    System.out.print("Enter Principal Amount: ");
	    loan.setPrincipalAmount(sc.nextDouble());
	    sc.nextLine(); 

	    System.out.print("Enter Interest Rate: ");
	    loan.setInterestRate(sc.nextDouble());
	    sc.nextLine(); 

	    System.out.print("Enter Loan Term (in months): ");
	    loan.setLoanTerm(sc.nextInt());
	    sc.nextLine(); 

	    System.out.print("Enter Loan Type (CarLoan/HomeLoan): ");
	    String loanTypeInput = sc.nextLine();
	    loan.setLoanType(LoanType.valueOf(loanTypeInput));

	    if (loan.getLoanType() == LoanType.HomeLoan) {
	        System.out.print("Enter Property Address: ");
	        loan.setPropertyAddress(sc.nextLine());

	        System.out.print("Enter Property Value: ");
	        loan.setPropertyValue(sc.nextInt());
	        sc.nextLine(); 
	    } else if (loan.getLoanType() == LoanType.CarLoan) {
	        System.out.print("Enter Car Model: ");
	        loan.setCarModel(sc.nextLine());

	        System.out.print("Enter Car Value: ");
	        loan.setCarValue(sc.nextInt());
	        sc.nextLine(); 
	    }

	    try {
	        iloan.applyLoan(loan);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public static void getAllLoanMain() {
			try {
				List <Loan> loans =iloan.getAllLoan();
				for (Loan loan : loans) {
					System.out.println(loan);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static void getLoanMain() {
		 System.out.print("Enter Loan ID: ");
         int loanId = sc.nextInt();
         try {
			iloan.getLoanById(loanId);
		} catch (InvalidLoanException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
	}
	public static void loanRepayment() {
		System.out.print("Enter Loan ID: ");
        int repayLoanId = sc.nextInt();
        System.out.print("Enter Repayment Amount: ");
        double repayAmount = sc.nextDouble();
        try {
			iloan.loanRepayment(repayLoanId, repayAmount);
		} catch (InvalidLoanException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
        
	}
	
	public static void  LoanStatusMain() {
		 System.out.print("Enter Loan ID: ");
         int loanId = sc.nextInt();
		 try {
			iloan.loanStatus(loanId);
		} catch (InvalidLoanException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
	}
	
	public static void CHeckEMI() {
		 System.out.print("Enter Loan ID: ");
         int loanId = sc.nextInt();
         try {
 			double val = iloan.calculateEMI(loanId);
 			System.out.println(" monthly EMI is:"+val);
 		} catch (InvalidLoanException e) {
             System.out.println("Error: " + e.getMessage());
         } catch (SQLException | ClassNotFoundException e) {
             System.out.println("Database Error: " + e.getMessage());
         } catch (Exception e) {
             System.out.println("Unexpected Error: " + e.getMessage());
         }
	}
	
	public static void CHeckInterest() {
		 System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        try {
			double val = iloan.calculateInterest(loanId);
			System.out.println("Interset is:"+val);
		} catch (InvalidLoanException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
	}
}
