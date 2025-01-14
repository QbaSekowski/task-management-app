package mate.academy.taskmanagementapp.service.dropbox;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(String fileName, MultipartFile multipartFile) throws IOException;

    MultipartFile getFile(String fileId);

    void deleteFile(String fileId);
}
