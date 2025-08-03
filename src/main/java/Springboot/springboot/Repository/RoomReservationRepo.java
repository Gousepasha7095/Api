package Springboot.springboot.Repository;

import Springboot.springboot.Entity.RoomReservastion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomReservationRepo extends JpaRepository<RoomReservastion, Long> {

    Optional<RoomReservastion> findByCustomerNameAndRoomType(String customerName, String roomType);
}
