package Springboot.springboot.Service;

import Springboot.springboot.Dto.TransactionRequestDto;
import Springboot.springboot.Entity.Transaction;
import Springboot.springboot.Entity.User;
import Springboot.springboot.Entity.UserAccount;
import Springboot.springboot.Repository.TransactionRepository;
import Springboot.springboot.Repository.UserRepository;
import Springboot.springboot.Response.UserTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

//    public UserTransactionResponse getUserWithTransactions(TransactionRequestDto dto) {
//        UserAccount account = userRepository.findByAccountNumberAndUserName(
//                dto.getAccountNumber(), dto.getUserName());
//
//        if (account == null) {
//            throw new RuntimeException("Account not found or username mismatch");
//        }
//
//        UserTransactionResponse response = new UserTransactionResponse();
//        response.setUserName(account.getUserName());
//        response.setBankName(account.getBankName());
//        response.setAccountNumber(account.getAccountNumber());
//        response.setAmount(account.getAmount());
//
//        List<Transaction> transactions = account.getTransactions().stream().map(tx -> {
//            Transaction t = new Transaction();
//            t.setId(tx.getId());
//            t.setAmount(tx.getAmount());
//            t.setType(tx.getType());
//            t.setTimeStamp(tx.getTimeStamp());
//            return t;
//        }).collect(Collectors.toList());
//
//        response.setTransactions(transactions);
//        return response;
//    }
}
