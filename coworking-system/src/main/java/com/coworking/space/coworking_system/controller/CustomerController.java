package com.coworking.space.coworking_system.controller;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/availablespaces")
    public String getAvailableSpaces(Model model){
        List<WorkSpace> spaces = customerService.getAvailableWorkSpaces();
        if(spaces.isEmpty()){
            model.addAttribute("message","No available workspaces at the moment" +
                    ", please come back later");
        }else{
            model.addAttribute("spaces",spaces);
        }
        return "available-spaces";
    }

    @PostMapping("/reserve/{spaceId}")
    public String makeReservation(@PathVariable int spaceId){
        customerService.makeReservation(spaceId);
        return "redirect:/availablespaces";
    }

    @GetMapping("/my-reservation")
    public String viewMyReservations(Model model, @AuthenticationPrincipal UserDetails userDetails){
        WorkSpace reservations = customerService.getReservationByCustomer(userDetails);
        if(reservations == null){
            model.addAttribute("message","You dont have any reservation");
        }else{
            model.addAttribute("reservation",reservations);
        }

        model.addAttribute("reservation",reservations);
        return "myreservation";
    }

    @DeleteMapping("/reservation/{spaceId}")
    public String cancelReservation(@PathVariable Integer spaceId){
        System.out.println("Deleting reservation with ID: " + spaceId);
        customerService.cancelReservationById(spaceId);
        return "redirect:/availablespaces";
    }
}