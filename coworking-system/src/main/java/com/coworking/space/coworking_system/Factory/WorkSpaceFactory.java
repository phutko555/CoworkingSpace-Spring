package com.coworking.space.coworking_system.Factory;
import com.coworking.space.coworking_system.model.WorkSpace;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class WorkSpaceFactory {
    public WorkSpace createWorkSpace(MultipartFile file) throws IOException{
        WorkSpace workSpace = new WorkSpace();
        workSpace.setImageType(file.getContentType());
        workSpace.setImageData(file.getBytes());
        workSpace.setImageName(file.getOriginalFilename());
        return workSpace;
    }
}
