package ar.edu.uno.sehda.controllers;

import ar.edu.uno.sehda.services.UploadFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadFilesController {

    @Autowired
    UploadFilesService uploadFilesService;

    @GetMapping("/uploadFile")
    public String files(MultipartFile file){
        return "uploadFile";
    }

    @PostMapping("/file")
    private ResponseEntity<String> uploadFile(MultipartFile file) throws Exception {
        return new ResponseEntity<>(uploadFilesService.uploadNewFile(file), HttpStatus.OK);
    }
 }
