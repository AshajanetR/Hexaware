package com.java.LoanManagement.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerTest {

	@Test
	public void testcosntructor() {
		Customer c = new Customer();
		assertNotNull(c);
		Customer c1 = new Customer(1,"John Doe", "john.doe@example.com", "9876543210", "123 Main Street, New York, NY", 700);
		assertEquals(1, c1.getCustomerId());
		assertEquals("John Doe", c1.getName());
		assertEquals("john.doe@example.com", c1.getEmailAddress());
		assertEquals("9876543210", c1.getPhoneNumber());
		assertEquals("123 Main Street, New York, NY", c1.getAddress());
		assertEquals(700, c1.getCreditScore());
	}
	
	@Test
	public void testgettersandsetters() {
		Customer c = new Customer();
		c.setCustomerId(1);
		c.setName("John Doe");
		c.setEmailAddress("john.doe@example.com");
		c.setPhoneNumber("9876543210");
		c.setAddress("123 Main Street, New York, NY");
		c.setCreditScore(700);
		assertEquals(1, c.getCustomerId());
		assertEquals("John Doe", c.getName());
		assertEquals("john.doe@example.com", c.getEmailAddress());
		assertEquals("9876543210", c.getPhoneNumber());
		assertEquals("123 Main Street, New York, NY", c.getAddress());
		assertEquals(700, c.getCreditScore());
		
	}
	
	@Test
	public void testtostring() {
		Customer c1 = new Customer(1,"John Doe", "john.doe@example.com", "9876543210", "123 Main Street, New York, NY", 700);
		String res ="Customer(customerId=1)";
		assertEquals(res,c1.toString() );
	}

}
