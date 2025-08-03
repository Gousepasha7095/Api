package Springboot.springboot;
import Springboot.springboot.Controller.AccountController;
import Springboot.springboot.Dto.TransactionRequestDto;
import Springboot.springboot.Entity.Transaction;
import Springboot.springboot.Entity.UserAccount;
import Springboot.springboot.Repository.TransactionRepository;
import Springboot.springboot.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private UserAccount testAccount;

    @BeforeEach
    public void setUp(){
        testAccount = new UserAccount();
        testAccount.setUserName("John Doe");
        testAccount.setBankName("Bank XYZ");
        testAccount.setAccountNumber(1234567890L);
        testAccount.setAmount(1000.0);
    }
    @Test
    public void testCreate_NewAccount_ReturnsCreated() {
        when(userRepository.findByAccountNumber(testAccount.getAccountNumber())).thenReturn(null);
        ResponseEntity<?> response = accountController.create(testAccount);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User Created", response.getBody());
       Mockito.verify(userRepository,times(1)).save(any(UserAccount.class));
    }
   @Test
    public void testConfilct(){
        when(userRepository.findByAccountNumber(testAccount.getAccountNumber())).thenReturn(testAccount);
        ResponseEntity<?> response=accountController.create(testAccount);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("AccountNumber Already Presented",response.getBody());
        Mockito.verify(userRepository,never()).save(any(UserAccount.class));
   }
   @Test
    public void testInternalServerError(){
       when(userRepository.findByAccountNumber(testAccount.getAccountNumber()))
               .thenThrow(new RuntimeException("Database Error"));
       ResponseEntity<?> response=accountController.create(testAccount);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
       assertEquals("Error User Creating",response.getBody());
   }
   @Test
    public void testDepositSuccess(){
       TransactionRequestDto dto = new TransactionRequestDto();
       dto.setUserName("John Doe");
       dto.setAccountNumber(1234567890L);
       dto.setAmount(500.0);

       when(userRepository.findByAccountNumberAndUserName(dto.getAccountNumber(),dto.getUserName()))
               .thenReturn(testAccount);
       ResponseEntity<?> response= accountController.deposit(dto);
       assertEquals(HttpStatus.OK,response.getStatusCode());
       Map<String,Object>body=(Map<String, Object>) response.getBody();
       assertEquals("Deposited Successfully",body.get("Message"));
       assertEquals(1500.0, body.get("New Balance"));
       Mockito.verify(transactionRepository,times(1)).save(any(Transaction.class));
       Mockito.verify(userRepository,times(1)).save(testAccount);
   }

   @Test
    public void testDepositFailure(){
       TransactionRequestDto dto = new TransactionRequestDto();
       dto.setUserName("John Doe");
       dto.setAccountNumber(1234567890L);
       dto.setAmount(500.0);
       when(userRepository.findByAccountNumberAndUserName(dto.getAccountNumber(),dto.getUserName()))
               .thenReturn(null);
       ResponseEntity<?> response= accountController.deposit(dto);
       assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
       assertEquals(
               "Missing Account number and User Name or Miss match the account number and username",
               response.getBody());
       Mockito.verify(transactionRepository,never()).save(any(Transaction.class));
       Mockito.verify(userRepository,never()).save(any(UserAccount.class));
   }
}
