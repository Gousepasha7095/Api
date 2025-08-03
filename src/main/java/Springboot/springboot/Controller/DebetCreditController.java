package Springboot.springboot.Controller;

import Springboot.springboot.Dto.DebitCreditDto;
import Springboot.springboot.Entity.DebitCredit;
import Springboot.springboot.Repository.DebitCreditRepo;
import Springboot.springboot.Service.DebitCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DebetCreditController {

    @Autowired
    private DebitCreditRepo debitCreditRepo;

    @Autowired
    private DebitCreditService debitCreditService;

    @PostMapping("/addDetails")
    public ResponseEntity<?> add(@RequestBody DebitCreditDto debitCreditDto){
        try{
            DebitCredit exdebitcredit= debitCreditService.findByKeyAndValue(debitCreditDto.getKey(),debitCreditDto.getValue());
            if (exdebitcredit != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("details already found");
            }
            DebitCredit debitCredit = new DebitCredit();
            debitCredit.setKey(debitCreditDto.getKey());
            debitCredit.setValue(debitCredit.getValue());
            debitCreditRepo.save(debitCredit);
            return ResponseEntity.status(HttpStatus.CREATED).body(debitCredit);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR CREATING");
        }
    }
}
