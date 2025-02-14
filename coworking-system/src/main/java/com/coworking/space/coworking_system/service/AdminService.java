package com.coworking.space.coworking_system.service;
import com.coworking.space.coworking_system.Factory.WorkSpaceFactory;
import com.coworking.space.coworking_system.model.User;
import com.coworking.space.coworking_system.model.WorkSpace;
import com.coworking.space.coworking_system.repository.UserRepo;
import com.coworking.space.coworking_system.repository.WorkSpaceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private WorkSpaceRepo workSpaceRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WorkSpaceFactory workSpaceFactory;
    @Transactional
    public void addWorkSpace(WorkSpace workSpace, MultipartFile file) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        if (username != null) {
            Optional<User> adminUser = userRepo.findByUsername(username);
            if (adminUser.isPresent()) {
                workSpace.setAdmin(adminUser.orElse(null));
            }
        }
       workSpace = workSpaceFactory.createWorkSpace(file);
        workSpaceRepo.save(workSpace);
    }

   public List<WorkSpace> getUploadedWorkspaces(){
        return workSpaceRepo.findAll();
   }

   public WorkSpace getSpacesById(int id){
        return workSpaceRepo.findById(id).get();
   }

}
