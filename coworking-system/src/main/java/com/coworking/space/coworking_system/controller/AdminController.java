package com.coworking.space.coworking_system.controller;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/adminpanel")
    public String showAdminPage(){
        return "admin-home-page";
    }

    @PostMapping("/uploadspace")
    public String uploadSpace(@ModelAttribute WorkSpace workSpace,
                              @RequestParam("file") MultipartFile multipartFile) throws IOException {
        adminService.addWorkSpace(workSpace,multipartFile);
        return "redirect:/adminpanel";
    }

    @GetMapping("/allworkspaces")
    public String getAllSpaces(Model model){
        List<WorkSpace> spaces = adminService.getUploadedWorkspaces();
        model.addAttribute("spaces",spaces);
        return "/all-spaces";
    }

    @GetMapping("/workspace/{spaceId}")
    public String getSpaceDetails(@PathVariable int spaceId, Model model){
        WorkSpace workspaces = adminService.getSpacesById(spaceId);
        model.addAttribute("spaces", workspaces);
        return "spaces-details";
    }

    @GetMapping("/spacephoto/{spaceId}")
    public ResponseEntity<byte[]> getSpaceImage(@PathVariable int spaceId) {
        WorkSpace reservation = adminService.getSpacesById(spaceId);
        if (reservation != null && reservation.getImageData() != null) {
            return ResponseEntity.ok().contentType(MediaType.valueOf(reservation.getImageType()))
                    .body(reservation.getImageData());
        }
        return ResponseEntity.notFound().build();
    }
}