package sn.smd.gestionbibliotheque.backend.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sn.smd.gestionbibliotheque.backend.service.FileStorageService;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.file.uploaddir}")
    private String rootLocation;

    @Override
    public String storeFile(MultipartFile file) {

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Fichier vide");
            }

            Path dirPath = Paths.get(rootLocation);
            Files.createDirectories(dirPath);

            // 🔥 sécurisation nom fichier
            String originalName = StringUtils.cleanPath(file.getOriginalFilename());

            String extension = "";
            int dotIndex = originalName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = originalName.substring(dotIndex);
            }

            String newFileName = UUID.randomUUID() + extension;

            Path targetLocation = dirPath.resolve(newFileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier stocké : {}", newFileName);

            return newFileName;

        } catch (IOException e) {
            throw new RuntimeException("Erreur stockage fichier", e);
        }
    }

    @Override
    public Resource getFile(String fileName) throws IOException {

        Path filePath = Paths.get(rootLocation)
                .resolve(fileName)
                .normalize();

        FileSystemResource resource = new FileSystemResource(filePath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("Fichier introuvable : " + fileName);
        }

        return resource;
    }

    @Override
    public String getBaseDirectory() {
        return rootLocation;
    }
}