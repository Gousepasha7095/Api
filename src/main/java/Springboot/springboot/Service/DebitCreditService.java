package Springboot.springboot.Service;

import Springboot.springboot.Entity.DebitCredit;
import Springboot.springboot.Repository.DebitCreditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitCreditService {

    @Autowired
    private DebitCreditRepo debitCreditRepo;

    public DebitCredit findByKeyAndValue(Long key,String value){
        return debitCreditRepo.findByKeyAndValue(key,value);
    }
}
