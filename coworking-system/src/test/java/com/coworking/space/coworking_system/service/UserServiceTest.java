package com.coworking.space.coworking_system.service;
import com.coworking.space.coworking_system.Enum.Role;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        userService.registerUser(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("hashedPassword", user.getPassword());
        verify(userRepo, times(1)).save(user);
    }
}
