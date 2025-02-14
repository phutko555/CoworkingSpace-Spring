package com.coworking.space.coworking_system.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.coworking.space.coworking_system.Enum.Role;
import com.coworking.space.coworking_system.model.Reservation;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.repository.ReservationRepo;
import com.coworking.space.coworking_system.repository.UserRepo;
import com.coworking.space.coworking_system.repository.WorkSpaceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private WorkSpaceRepo workSpaceRepo;

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CustomerService customerService;

    private User user;
    private WorkSpace workSpace;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setRole(Role.CUSTOMER);

        workSpace = new WorkSpace();
        workSpace.setWorkspaceId(1);
        workSpace.setAvailabilityStatus(String.valueOf(true));

        reservation = new Reservation();
        reservation.setWorkSpace(workSpace);
        reservation.setCustomer(user);
    }

    @Test
    void testGetAvailableWorkSpaces() {
        when(workSpaceRepo.findByAvailabilityStatusTrue()).thenReturn(List.of(workSpace));

        List<WorkSpace> result = customerService.getAvailableWorkSpaces();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }


    @Test
    void testCancelReservationById() {
        when(reservationRepo.findById(1)).thenReturn(Optional.of(reservation));

        customerService.cancelReservationById(1);

        verify(reservationRepo, times(1)).delete(reservation);
    }
}

