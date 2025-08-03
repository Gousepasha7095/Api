package Springboot.springboot.Response;

import Springboot.springboot.Dto.TransactionDto;
import Springboot.springboot.Entity.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class UserTransactionResponse {
    private String userName;
    private String bankName;
    private Long accountNumber;
    private double amount;
    private List<TransactionDto> transactionDtos;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<TransactionDto> getTransactions() {
        return transactionDtos;
    }

    public void setTransactions(List<TransactionDto> transactionDtos) {
        this.transactionDtos = transactionDtos;
    }
}
