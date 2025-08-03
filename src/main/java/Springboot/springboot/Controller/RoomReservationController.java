package Springboot.springboot.Controller;

import Springboot.springboot.Entity.RoomReservastion;
import Springboot.springboot.Service.RoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RoomReservationController {

    @Autowired
    RoomReservationService roomReservationService;

    @PostMapping("/bookRoom")
    public ResponseEntity<RoomReservastion> bookRoom(@RequestBody RoomReservastion roomReservastion){
        RoomReservastion cnfrmReservation = roomReservationService.bookRoom(roomReservastion);
        return ResponseEntity.ok(cnfrmReservation);
    }
}
