package mate.academy.taskmanagementapp.service.dropbox.impl;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import java.io.InputStream;
import mate.academy.taskmanagementapp.service.dropbox.DropboxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DropboxServiceImpl implements DropboxService {
    @Value("${dropbox.access.token}")
    private String accessToken;

    @Value("${dropbox.upload.url}")
    private String uploadPath;

    @Value("${dropbox.client.identifier}")
    private String clientIdentifier;

    @Transactional
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(clientIdentifier).build();
        DbxClientV2 dropboxClient = new DbxClientV2(config, accessToken);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            FileMetadata metadata = dropboxClient.files()
                    .uploadBuilder(uploadPath + "/" + multipartFile.getOriginalFilename())
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream);
            return metadata.getId();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to Dropbox: " + e.getMessage(), e);
        }
    }
}
