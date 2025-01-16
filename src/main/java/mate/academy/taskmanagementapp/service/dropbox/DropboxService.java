package mate.academy.taskmanagementapp.service.dropbox;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(MultipartFile multipartFile) throws IOException, InterruptedException;
}
