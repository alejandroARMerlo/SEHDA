package ar.edu.uno.sehda.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFilesService {

    String uploadNewFile (MultipartFile file) throws Exception;
}
