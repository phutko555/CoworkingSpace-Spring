package com.coworking.space.coworking_system.service;
import com.coworking.space.coworking_system.Enum.Role;
import com.coworking.space.coworking_system.model.Reservation;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.repository.ReservationRepo;
import com.coworking.space.coworking_system.repository.UserRepo;
import com.coworking.space.coworking_system.repository.WorkSpaceRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
   private WorkSpaceRepo workSpaceRepo;

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public List<WorkSpace> getAvailableWorkSpaces(){
        return workSpaceRepo.findByAvailabilityStatusTrue();
    }

    @Transactional
    public void makeReservation(int spaceId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        if (username != null) {
            Optional<User> customerUserOpt = userRepo.findByUsername(username);

            if (customerUserOpt.isPresent()) {
                User customerUser = customerUserOpt.get();
                if (customerUser.getRole() == Role.CUSTOMER) {
                    WorkSpace workSpace = workSpaceRepo.findById(spaceId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid workspace ID"));

                    Reservation reservation = new Reservation();
                    reservation.setWorkSpace(workSpace);
                    reservation.setCustomer(customerUser);
                    reservationRepo.save(reservation);
                    workSpace.setAvailabilityStatus(String.valueOf(false));
                    workSpaceRepo.save(workSpace);
                } else {
                    throw new IllegalArgumentException("User is not a valid customer");
                }
            } else {
                throw new IllegalArgumentException("User not found");
            }
        }
    }
    @Transactional
    public WorkSpace getReservationByCustomer(UserDetails userDetails){
        User customer = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();
        return workSpaceRepo.findByReservationCustomer(customer);
    }
    @Transactional
    public void cancelReservationById(int id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        if (reservation.getCustomer() != null) {
            reservation.getCustomer().setReservation(null);
        }
        if (reservation.getWorkSpace() != null) {
            reservation.getWorkSpace().setReservation(null);
        }
        reservationRepo.delete(reservation);
        reservation.getWorkSpace().setAvailabilityStatus(String.valueOf(true));
        reservationRepo.flush();
    }
}