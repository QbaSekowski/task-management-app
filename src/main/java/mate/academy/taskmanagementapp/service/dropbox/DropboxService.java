package mate.academy.taskmanagementapp.service.dropbox;

import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(MultipartFile multipartFile);
}
