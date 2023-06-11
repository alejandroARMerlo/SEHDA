package ar.edu.uno.sehda.services.imp;

import ar.edu.uno.sehda.services.UploadFilesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFilesServiceImp implements UploadFilesService {


    @Override
    public String uploadNewFile(MultipartFile file) throws Exception {

        try{
            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            if (fileOriginalName.endsWith(".mp3") || fileOriginalName.endsWith(".jpg") || fileOriginalName.endsWith(".mp4")){

            }else{
                return "This file not is allowed!";
            }
            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            File folder = new File("src/main/resources/fileTest");
            if (!folder.exists()){
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/fileTest/"+ newFileName);
            Files.write(path, bytes);
            return "File upload Ok";


        }catch (Exception e){
            throw new Exception((e.getMessage()));
        }
    }
}
