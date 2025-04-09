package com.java.LoanManagement.main;

import java.util.List;
import java.util.Scanner;

import com.java.LoanManagement.dao.ILoanRepository;
import com.java.LoanManagement.dao.ILoanRepositoryImpl;

import com.java.LoanManagement.model.CarLoan;
import com.java.LoanManagement.model.Customer;
import com.java.LoanManagement.model.HomeLoan;
import com.java.LoanManagement.model.Loan;
import com.java.LoanManagement.model.loanType;
 
public class LoanManagement {

    static Scanner sc = new Scanner(System.in);
    static ILoanRepository iloan = new ILoanRepositoryImpl();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--------------------------------------\n");
            System.out.println("O P T I O N S");
            System.out.println("-------------");
            System.out.println("1. Apply Loan");
            System.out.println("2. Get All Loans");
            System.out.println("3. Get Loan by ID");
            System.out.println("4. Loan Repayment");
            System.out.println("5. Check Loan Status");
            System.out.println("6. Check EMI");
            System.out.println("7. Check Interest");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
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
                	CheckEMI();
                	break;
                case 7:
                	CheckInterest();
                	break;
                case 8:
                	System.out.println("Exiting...");
                	break;
                default:
                	System.out.println("Invalid choice, please try again.");
                	break;
            }

        } while (choice != 8);
    } 

    public static void ApplyLoanMain() {
        Loan loan = null;

        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Principal Amount: ");
        double principal = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Interest Rate: ");
        double rate = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Loan Term (in months): ");
        int term = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Loan Type (CarLoan/HomeLoan): ");
        String loanTypeInput = sc.nextLine();
        loanType type = loanType.valueOf(loanTypeInput);

        if (type == loanType.HomeLoan) {
            HomeLoan homeLoan = new HomeLoan();
            homeLoan.setLoanType(type);

            System.out.print("Enter Property Address: ");
            homeLoan.setPropertyAddress(sc.nextLine());

            System.out.print("Enter Property Value: ");
            homeLoan.setPropertyValue(sc.nextInt());
            sc.nextLine();

            loan = homeLoan;
        } else if (type == loanType.CarLoan) {
            CarLoan carLoan = new CarLoan();
            carLoan.setLoanType(type);

            System.out.print("Enter Car Model: ");
            carLoan.setCarModel(sc.nextLine());

            System.out.print("Enter Car Value: ");
            carLoan.setCarValue(sc.nextInt());
            sc.nextLine();

            loan = carLoan;
        }

        loan.setPrincipalAmount(principal);
        loan.setInterestRate(rate);
        loan.setLoanTerm(term);

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        loan.setCustomer(customer);

        try {
            iloan.applyLoan(loan);
        } catch (Exception e) {
            System.out.println("Error applying loan: " + e.getMessage());
        }
    }

    public static void getAllLoanMain() {
        try {
            List<Loan> loans = iloan.getAllLoan();
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        } catch (Exception e) {
            System.out.println("Error fetching loans: " + e.getMessage());
        }
    }

    public static void getLoanMain() {
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        try {
            Loan loan = iloan.getLoanById(loanId);
            System.out.println(loan);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void loanRepayment() {
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        System.out.print("Enter Repayment Amount: ");
        double amount = sc.nextDouble();
        try {
            iloan.loanRepayment(loanId, amount);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void LoanStatusMain() {
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        try {
            String status = iloan.loanStatus(loanId);
            System.out.println("Loan Status: " + status);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void CheckEMI() {
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        try {
            double emi = iloan.calculateEMI(loanId);
            System.out.printf("Monthly EMI: ₹%.2f\n", emi);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void CheckInterest() {
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        try {
            double interest = iloan.calculateInterest(loanId);
            System.out.printf("Estimated Interest: ₹%.2f\n", interest);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}