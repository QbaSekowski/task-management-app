package mate.academy.taskmanagementapp.service.dropbox.impl;

import java.io.IOException;
import mate.academy.taskmanagementapp.service.dropbox.DropboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DropboxServiceImpl implements DropboxService {
    @Transactional
    @Override
    public String uploadFile(String fileName, MultipartFile multipartFile) throws IOException {
        return "";
    }

    @Override
    public MultipartFile getFile(String fileId) {
        return null;
    }

    @Transactional
    @Override
    public void deleteFile(String fileId) {

    }
}
