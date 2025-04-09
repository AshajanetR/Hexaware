package com.java.LoanManagement.model;

import static org.junit.Assert.*;
import org.junit.Test;


public class CarLoanTest {

	  @Test
	    public void testGettersAndSetters() {
		  CarLoan loan = new CarLoan();
		  loan.setCarModel("suv");
		  loan.setCarValue(2390);
		  assertEquals("suv", loan.getCarModel());
		  assertEquals(2390, loan.getCarValue());
	    }

	    @Test
	    public void testConstructor() {
	    	 CarLoan loan = new CarLoan();
	    	 assertNotNull(loan);
	    	 CarLoan loan1 = new CarLoan("suv",2389);
	    	 assertEquals("suv", loan1.getCarModel());
	    	 assertEquals(2389, loan1.getCarValue());
	    	 
	    }
        
	    @Test
	    public void testtostring() {
	    	CarLoan loan1 = new CarLoan("suv",2389);
	    	String res = "Loan(loanId=0, customer=null, principalAmount=0.0, interestRate=0.0, loanTerm=0, loanType=null, loanStatus=null), carModel=suv, carValue=2389)";
	    	assertEquals(res, loan1.toString());
	    }


}
