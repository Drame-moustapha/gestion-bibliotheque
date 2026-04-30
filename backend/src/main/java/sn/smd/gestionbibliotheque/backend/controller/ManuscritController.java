package sn.smd.gestionbibliotheque.backend.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.smd.gestionbibliotheque.backend.entity.Manuscrit;
import sn.smd.gestionbibliotheque.backend.service.FileStorageService;
import sn.smd.gestionbibliotheque.backend.service.ManuscritService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manuscrits")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ManuscritController {

    private final ManuscritService manuscritService;
    private final FileStorageService fileStorageService;

    private static final Logger log = LoggerFactory.getLogger(ManuscritController.class);

    // CREATE
    @PostMapping
    public ResponseEntity<Manuscrit> create(@RequestBody Manuscrit manuscrit) {
        return new ResponseEntity<>(manuscritService.create(manuscrit), HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Manuscrit>> getAll() {
        List<Manuscrit> list = manuscritService.getAll();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(list);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Manuscrit> getById(@PathVariable Long id) {
        return ResponseEntity.ok(manuscritService.getById(id));
    }

    // GET BY AUTHOR
    @GetMapping("/auteur/{id}")
    public ResponseEntity<List<Manuscrit>> getByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(manuscritService.getByAuteur(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Manuscrit> update(@PathVariable Long id,
                                            @RequestBody Manuscrit manuscrit) {
        return ResponseEntity.ok(manuscritService.update(id, manuscrit));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        manuscritService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(manuscritService.count());
    }

    // =========================
    // FILE UPLOAD
    // =========================

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Fichier vide");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("Fichier trop volumineux (max 5MB)");
        }

        try {
            String fileName = fileStorageService.storeFile(file);

            log.info("UPLOAD SUCCESS : {}", fileName);

            return ResponseEntity.ok(fileName);

        } catch (Exception e) {
            log.error("UPLOAD ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur upload fichier");
        }
    }

    // DOWNLOAD FILE
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws IOException {

        Resource file = fileStorageService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}