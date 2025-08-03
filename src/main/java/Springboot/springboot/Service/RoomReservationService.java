package Springboot.springboot.Service;

import Springboot.springboot.Entity.RoomReservastion;
import Springboot.springboot.Repository.RoomReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomReservationService {

    @Autowired
    RoomReservationRepo roomReservationRepo;


    public RoomReservastion bookRoom(RoomReservastion roomReservastion) {
        Optional<RoomReservastion> existingReservation = roomReservationRepo
                .findByCustomerNameAndRoomType(roomReservastion.getCustomerName(), roomReservastion.getRoomType());

        if (existingReservation.isPresent()) {
            throw new IllegalArgumentException("Room already booked for " + roomReservastion.getCustomerName());
        }
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        roomReservastion.setConfirmed(true);
        return roomReservationRepo.save(roomReservastion);
    }
}
