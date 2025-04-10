package com.java.LoanManagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Customer {
	private int customerId;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private int creditScore;
    
    @Override
    public String toString() {
        return "Customer(customerId=" + customerId + ")";
    }
}
