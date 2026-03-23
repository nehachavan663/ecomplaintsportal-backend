package com.ecomplaintsportal.studentProfile;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecomplaintsportal.LRE.User;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@RestController
@RequestMapping("/api/studentProfile")
public class StudentProfileController {

    @Autowired
    private StudentProfileService profileService;

    @Autowired
    private StudentProfileImageService imageService;

    @Autowired
    private GridFsTemplate gridFsTemplate;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{id}")
    public User getStudentProfile(@PathVariable String id){
        return profileService.getStudentProfile(id);
    }

    @PutMapping("/{id}")
    public User updateProfile(@PathVariable String id, @RequestBody User user) {

        User updatedUser = profileService.updateStudentProfile(id, user);

        messagingTemplate.convertAndSend(
            "/topic/profile/" + id,
            updatedUser
        );

        return updatedUser;
    }
 
    @PutMapping("/image/{id}")
    public String uploadImage(@PathVariable String id,
                              @RequestParam("file") MultipartFile file) throws IOException {

        String imageId = imageService.storeImage(file);

        profileService.saveImage(id,imageId);

        // Get updated user
        User updatedUser = profileService.getStudentProfile(id);

        // Send live profile update
        messagingTemplate.convertAndSend(
            "/topic/profile/" + id,
            updatedUser
        );

        return imageId;
    }
    
    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageId) throws IOException {

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(imageId))
        );

        GridFsResource resource = gridFsTemplate.getResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,"image/jpeg")
                .body(resource.getInputStream().readAllBytes());
    }

    @GetMapping("/summary/{id}")
    public ComplaintSummaryDTO getComplaintSummary(@PathVariable String id){
        return profileService.getComplaintSummary(id);
    }
}