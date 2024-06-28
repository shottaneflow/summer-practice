package com.practice.backend.dto;
import java.math.BigDecimal;

public class MoneyTransferRequest {
	
    private Long secondAccountNumber;
    private BigDecimal amount;

    public MoneyTransferRequest() {
    }

    public MoneyTransferRequest(Long secondAccountNumber, BigDecimal amount) {
        this.secondAccountNumber = secondAccountNumber;
        this.amount = amount;
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
}