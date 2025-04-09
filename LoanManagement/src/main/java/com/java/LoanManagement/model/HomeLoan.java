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

public class HomeLoan extends Loan {
    private String propertyAddress;
    private int propertyValue;
    
    @Override
    public String toString() {
        return super.toString() +
               ", propertyAddress=" + propertyAddress +
               ", propertyValue=" + propertyValue + ")";
    }

}