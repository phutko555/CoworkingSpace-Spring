package com.coworking.space.coworking_system.repository;
import com.coworking.space.coworking_system.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
}
