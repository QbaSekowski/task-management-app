package mate.academy.taskmanagementapp.service.dropbox;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(String fileName, MultipartFile multipartFile) throws IOException, InterruptedException;

    Resource downloadFile(String dropboxId) throws IOException, InterruptedException;
}
