package Springboot.springboot.Repository;

import Springboot.springboot.Entity.DebitCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCreditRepo extends JpaRepository<DebitCredit, Long> {

    DebitCredit findByKeyAndValue(Long key, String value);

}
