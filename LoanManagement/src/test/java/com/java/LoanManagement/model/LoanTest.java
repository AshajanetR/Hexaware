package com.java.LoanManagement.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoanTest {
	
	
	private Loan l;
    private Loan l1;
    
	@Before
	public void setup() {
		 l = new Loan();
	     l1 = new Loan(1,1,500000,7.50,120,LoanType.CarLoan,LoanStatus.Pending,"123 Main Street, New York",100000,"Toyota Camry 2022",550000);
	}

	@Test
	public void testconstructor() {
		
		assertNotNull(l);
		
		assertEquals(1,l1.getLoanId());
		assertEquals(1,l1.getCustomerId());
		assertEquals(500000,l1.getPrincipalAmount(),2);
		assertEquals(7.50,l1.getInterestRate(),2);
		assertEquals(120,l1.getLoanTerm());
		assertEquals(LoanType.CarLoan,l1.getLoanType());
		assertEquals(LoanStatus.Pending,l1.getLoanStatus());
		assertEquals("123 Main Street, New York",l1.getPropertyAddress());
		assertEquals(100000,l1.getPropertyValue(),2);
		assertEquals("Toyota Camry 2022",l1.getCarModel());
		assertEquals(550000,l1.getCarValue(),2);
	}
	
	@Test
	public void testsettersandgetters() {
		l.setLoanId(1);
		l.setCustomerId(1);
		l.setPrincipalAmount(500000);
		l.setInterestRate(7.50);
		l.setLoanTerm(120);
		l.setLoanType(LoanType.CarLoan);
		l.setLoanStatus(LoanStatus.Pending);
		l.setPropertyAddress("123 Main Street, New York");
		l.setPropertyValue(100000);
		l.setCarModel("Toyota Camry 2022");
		l.setCarValue(550000);
		
		assertEquals(1,l1.getLoanId());
		assertEquals(1,l1.getCustomerId());
		assertEquals(500000,l1.getPrincipalAmount(),2);
		assertEquals(7.50,l1.getInterestRate(),2);
		assertEquals(120,l1.getLoanTerm());
		assertEquals(LoanType.CarLoan,l1.getLoanType());
		assertEquals(LoanStatus.Pending,l1.getLoanStatus());
		assertEquals("123 Main Street, New York",l1.getPropertyAddress());
		assertEquals(100000,l1.getPropertyValue(),2);
		assertEquals("Toyota Camry 2022",l1.getCarModel());
		assertEquals(550000,l1.getCarValue(),2);
	}
	
	@Test
	public void testtostring() {
		String res = "Loan(loanId=1, customerId=1, principalAmount=500000.0, interestRate=7.5, loanTerm=120, loanType=CarLoan, loanStatus=Pending, propertyAddress=123 Main Street, New York, propertyValue=100000, carModel=Toyota Camry 2022, carValue=550000)";
		assertEquals(res, l1.toString());
	}

}
