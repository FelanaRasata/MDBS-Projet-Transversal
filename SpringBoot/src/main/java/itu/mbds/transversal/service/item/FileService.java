package itu.mbds.transversal.service.item;

import itu.mbds.transversal.utils.enumeration.Message;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileService {

    @Value("${filePath}")
    private String basePath;

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif"));
    }

    private String getFilename(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();

        assert fileName != null;
        if (fileName.contains(".")) {
            return System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
        } else {
            return null;
        }
    }


    public String uploadFile(MultipartFile multipartFile) {

        if (!isImage(multipartFile)) {
            throw new IllegalArgumentException(Message.UPLOAD_NO_PICTURE.toString());
        }

        String filename = getFilename(multipartFile);

        if (filename == null) {
            throw new IllegalArgumentException(Message.UPLOAD_NO_PICTURE.toString());
        }

        return uploadFile(filename, multipartFile);
    }

    private String uploadFile(String fileName, MultipartFile multipartFile) {

        Path path = Path.of(basePath + fileName);

        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalArgumentException(Message.UPLOAD_FAILED.toString());
    }

}
