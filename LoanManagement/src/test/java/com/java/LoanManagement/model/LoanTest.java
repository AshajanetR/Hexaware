package com.java.LoanManagement.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoanTest {

	@Test
    public void testConstructor() {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "9876543210", "123 Main Street, NY", 750);
        Loan loan = new Loan(1001, customer, 500000, 7.5, 24, loanType.CarLoan, loanStatus.Pending);

        assertEquals(1001, loan.getLoanId());
        assertEquals(customer, loan.getCustomer());
        assertEquals(500000, loan.getPrincipalAmount(), 0.001);
        assertEquals(7.5, loan.getInterestRate(), 0.001);
        assertEquals(24, loan.getLoanTerm());
        assertEquals(loanType.CarLoan, loan.getLoanType());
        assertEquals(loanStatus.Pending, loan.getLoanStatus());
    }

    @Test
    public void testGettersAndSetters() {
        Customer customer = new Customer();
        Loan loan = new Loan();

        loan.setLoanId(2002);
        loan.setCustomer(customer);
        loan.setPrincipalAmount(300000);
        loan.setInterestRate(6.9);
        loan.setLoanTerm(36);
        loan.setLoanType(loanType.HomeLoan);
        loan.setLoanStatus(loanStatus.Pending);

        assertEquals(2002, loan.getLoanId());
        assertEquals(customer, loan.getCustomer());
        assertEquals(300000, loan.getPrincipalAmount(), 0.001);
        assertEquals(6.9, loan.getInterestRate(), 0.001);
        assertEquals(36, loan.getLoanTerm());
        assertEquals(loanType.HomeLoan, loan.getLoanType());
        assertEquals(loanStatus.Pending, loan.getLoanStatus());
    }

    @Test
    public void testToString() {
        Customer customer = new Customer(2, "Alice", "alice@example.com", "1234567890", "456 Elm Street, CA", 720);
        Loan loan = new Loan(3003, customer, 200000, 8.0, 12, loanType.CarLoan, loanStatus.Pending);
        String expected = "Loan(loanId=3003, customer=" + customer.toString() +
                          ", principalAmount=200000.0, interestRate=8.0, loanTerm=12, loanType=CarLoan, loanStatus=Pending)";
        assertEquals(expected, loan.toString());
    }

}
