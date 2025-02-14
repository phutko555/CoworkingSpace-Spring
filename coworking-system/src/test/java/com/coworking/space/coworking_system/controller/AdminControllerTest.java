package com.coworking.space.coworking_system.controller;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

class AdminControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    AdminControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testUploadSpace() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "image-data".getBytes());
        WorkSpace workSpace = new WorkSpace();

        doNothing().when(adminService).addWorkSpace(any(WorkSpace.class), any());

        mockMvc.perform(multipart("/uploadspace")
                        .file(file)
                        .param("type", "Private Office")
                        .param("price", "100.0"))
                .andExpect(redirectedUrl("/adminpanel"));

        verify(adminService, times(1)).addWorkSpace(any(WorkSpace.class), any());
    }
}

