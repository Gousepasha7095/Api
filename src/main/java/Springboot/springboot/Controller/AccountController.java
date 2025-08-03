package Springboot.springboot.Controller;

import Springboot.springboot.Dto.TransactionDto;
import Springboot.springboot.Dto.TransactionRequestDto;
import Springboot.springboot.Entity.Transaction;
import Springboot.springboot.Entity.UserAccount;
import Springboot.springboot.Repository.TransactionRepository;
import Springboot.springboot.Repository.UserRepository;
import Springboot.springboot.Response.UserTransactionResponse;
import Springboot.springboot.Service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    @PostMapping("/addUserAccount")
    public ResponseEntity<?> create(@RequestBody UserAccount userAccount){
        try{
            UserAccount account= userRepository.findByAccountNumber(userAccount.getAccountNumber());
            if (account != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("AccountNumber Already Presented");
            }
            UserAccount ua= new UserAccount();
            ua.setUserName(userAccount.getUserName());
            ua.setBankName(userAccount.getBankName());
            ua.setAccountNumber(userAccount.getAccountNumber());
            ua.setAmount(userAccount.getAmount());

            userRepository.save(ua);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error User Creating");
        }
    }

    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<?>deposit(@RequestBody TransactionRequestDto transactionRequestDto){
        try{
            UserAccount account=userRepository.findByAccountNumberAndUserName
                    (transactionRequestDto.getAccountNumber(),transactionRequestDto.getUserName());
            if(account == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Missing Account number and User Name or Miss match the account number and username");
            }
            account.setAmount(account.getAmount() + transactionRequestDto.getAmount());

            Transaction tx=new Transaction();
            tx.setAmount(transactionRequestDto.getAmount());
            tx.setType("DEPOSIT");
            tx.setTimeStamp(LocalDateTime.now());
            tx.setAccount(account);

            transactionRepository.save(tx);

            userRepository.save(account);

            return ResponseEntity.ok(Map.of
                    ("Message","Deposited Successfully","New Balance", account.getAmount()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong"+e.getMessage());
        }
    }

    @PostMapping("/withdraw")
      public ResponseEntity<?>withdraw(@RequestBody TransactionRequestDto transactionRequestDto) {
        try {
            UserAccount account = userRepository.findByAccountNumberAndUserName
                    (transactionRequestDto.getAccountNumber(), transactionRequestDto.getUserName());
            if (account == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or MissMatch  Account number and User Name");
            }
            if (account.getAmount() < transactionRequestDto.getAmount()) {
                throw new RuntimeException("Insufficient Balance");
            }
            account.setAmount(account.getAmount() - transactionRequestDto.getAmount());

            Transaction tx = new Transaction();
            tx.setAmount(transactionRequestDto.getAmount());
            tx.setType("WITHDRAW");
            tx.setTimeStamp(LocalDateTime.now());
            tx.setAccount(account);
            transactionRepository.save(tx);
            userRepository.save(account);

            return ResponseEntity.ok(Map.of(
                    "message", "Withdrawal successful",
                    "newBalance", account.getAmount()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong" + e.getMessage());
        }
    }
   @PostMapping("/getUserWithTransactions")
   public UserTransactionResponse getUserWithTransactions(@RequestBody TransactionRequestDto dto) {
       UserAccount account = userRepository.findByAccountNumberAndUserName(
               dto.getAccountNumber(), dto.getUserName());

       if (account == null) {
           throw new RuntimeException("Account not found or username mismatch");
       }

       UserTransactionResponse response = new UserTransactionResponse();
       response.setUserName(account.getUserName());
       response.setBankName(account.getBankName());
       response.setAccountNumber(account.getAccountNumber());
       response.setAmount(account.getAmount());

       List<TransactionDto> txDtos = account.getTransactions().stream().map(tx -> {
           TransactionDto t = new TransactionDto();
           t.setId(tx.getId());
           t.setAmount(tx.getAmount());
           t.setType(tx.getType());
           t.setTimeStamp(tx.getTimeStamp());
           return t;
       }).collect(Collectors.toList());

       response.setTransactions(txDtos);
       return response;
   }
}
