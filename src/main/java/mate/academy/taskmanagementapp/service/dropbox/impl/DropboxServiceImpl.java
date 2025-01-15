package mate.academy.taskmanagementapp.service.dropbox.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import mate.academy.taskmanagementapp.service.dropbox.DropboxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DropboxServiceImpl implements DropboxService {
    @Value("${dropbox.access.token}")
    private String accessToken;

    @Value("${dropbox.upload.url}")
    private String uploadPath;

    @Value("${dropbox.download.url}")
    private String downloadPath;

    @Transactional
    @Override
    public String uploadFile(String fileName, MultipartFile multipartFile) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadPath))
                .header("Authorization",
                        "Bearer " + accessToken)
                .header("Dropbox-API-Arg",
                        "{\"path\": \"/"
                                + multipartFile.getOriginalFilename()
                                + "\", \"mode\": \"add\", \"autorename\": true, \"mute\": false}")
                .header("Content-Type",
                        "application/octet-stream")
                .POST(HttpRequest.BodyPublishers.ofByteArray(multipartFile.getBytes()))
                .build();
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response.body(), new TypeReference<>() {});
        return (String) map.get("id");
    }

    @Override
    public Resource downloadFile(String dropboxId) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(downloadPath))
                .header("Authorization",
                        "Bearer " + accessToken)
                .header("Dropbox-API-Arg",
                        "{\"path\": \"" + dropboxId + "\"}")
                .GET()
                .build();
        HttpResponse<byte[]> response;
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        return new ByteArrayResource(response.body());
    }
}
