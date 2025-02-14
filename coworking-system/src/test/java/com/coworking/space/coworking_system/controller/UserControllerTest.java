package com.coworking.space.coworking_system.controller;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

class UserControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    UserControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        doNothing().when(userService).registerUser(any(User.class));

        mockMvc.perform(post("/signup")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).registerUser(any(User.class));
    }
}
