package Springboot.springboot.Repository;

import Springboot.springboot.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount,Long> {
    UserAccount findByAccountNumber(Long accountNumber);

    UserAccount findByAccountNumberAndUserName(Long accountNumber,String userName);

}
