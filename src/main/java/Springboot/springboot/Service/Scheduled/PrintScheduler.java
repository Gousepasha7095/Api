package Springboot.springboot.Service.Scheduled;

import Springboot.springboot.Entity.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PrintScheduler {

    @Scheduled(fixedRate = 5000)
    public void printSchedule(){
        System.out.println("Scheduled task running at "+ System.currentTimeMillis());
    }

//    @Scheduled(fixedRate = 60*60*1000)
//   public void cancelpendingstaus(){
//
//        List<Transaction> pendingTransaction=trepo.findBystaus("Pending");
//        LocalDateTime cuttofftime= LocalDateTime.now().minusHours(48);
//
//        for(Transaction t: pendingTransaction){
//            if(t.createdAt().isBefore(cuttofftime)){
//                t.setStaus("Cancel");
//                trepo.save(t);
//                System.out.println("Transaction ID " + t.getId());
//            }
//        }
//    }
}
