package com.coworking.space.coworking_system.repository;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.model.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkSpaceRepo extends JpaRepository<WorkSpace,Integer> {
    List<WorkSpace> findByAvailabilityStatusTrue();
    WorkSpace findByReservationCustomer(User customer);

}
