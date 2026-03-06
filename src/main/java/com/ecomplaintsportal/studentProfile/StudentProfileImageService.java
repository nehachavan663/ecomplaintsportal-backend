package com.ecomplaintsportal.studentProfile;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentProfileImageService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public String storeImage(MultipartFile file) throws IOException {

        ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        return id.toString();
    }
}