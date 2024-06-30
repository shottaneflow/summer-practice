package com.practice.frontend.dto;
import java.math.BigDecimal;

public class MoneyTransferRequest {
	
    private Long secondAccountNumber;
    private BigDecimal amount;
    private String pincode;

    public MoneyTransferRequest() {
    }

    public MoneyTransferRequest(Long secondAccountNumber, BigDecimal amount,
                                String pincode) {
        this.secondAccountNumber = secondAccountNumber;
        this.amount = amount;
        this.pincode=pincode;
    }

    public Long getSecondAccountNumber() {
        return secondAccountNumber;
    }

    public void setSecondAccountNumber(Long secondAccountNumber) {
        this.secondAccountNumber = secondAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPincode(){return this.pincode;}

    public void setPincode(String pincode){this.pincode=pincode;}
}