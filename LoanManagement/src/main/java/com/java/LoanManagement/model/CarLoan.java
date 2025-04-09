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

public class CarLoan extends Loan {
    private String carModel;
    private int carValue;
    
    @Override
    public String toString() {
        return super.toString() +
               ", carModel=" + carModel +
               ", carValue=" + carValue + ")";
    }

}
