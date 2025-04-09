package com.java.LoanManagement.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class HomeLoanTest {
	 @Test
	    public void testGettersAndSetters() {
		  HomeLoan loan = new HomeLoan();
		  loan.setPropertyAddress("2nd street brindha nagar");
		  loan.setPropertyValue(200000);
		  assertEquals("2nd street brindha nagar", loan.getPropertyAddress());
		  assertEquals(200000, loan.getPropertyValue());
	    }

	    @Test
	    public void testConstructor() {
	    	 HomeLoan loan1 = new HomeLoan();
	    	 assertNotNull(loan1);
	    	 HomeLoan loan = new HomeLoan("2nd street brindha nagar",200000);
	    	 assertEquals("2nd street brindha nagar", loan.getPropertyAddress());
	    	 assertEquals(200000, loan.getPropertyValue());
	    	 
	    }
     
	    @Test
	    public void testtostring() {
	    	HomeLoan loan = new HomeLoan("2nd street brindha nagar",200000);
	    	String res = "Loan(loanId=0, customer=null, principalAmount=0.0, interestRate=0.0, loanTerm=0, loanType=null, loanStatus=null), propertyAddress=2nd street brindha nagar, propertyValue=200000)";
	    	assertEquals(res, loan.toString());
	    }
}
