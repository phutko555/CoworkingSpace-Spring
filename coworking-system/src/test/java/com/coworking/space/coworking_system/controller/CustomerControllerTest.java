package com.coworking.space.coworking_system.controller;

import com.coworking.space.coworking_system.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

class CustomerControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    CustomerControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testMakeReservation() throws Exception {
        int spaceId = 1;

        doNothing().when(customerService).makeReservation(spaceId);

        mockMvc.perform(post("/reserve/{spaceId}", spaceId))
                .andExpect(redirectedUrl("/availablespaces"));

        verify(customerService, times(1)).makeReservation(spaceId);
    }
}
