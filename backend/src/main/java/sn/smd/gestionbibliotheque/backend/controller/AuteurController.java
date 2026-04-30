package sn.smd.gestionbibliotheque.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.smd.gestionbibliotheque.backend.entity.Auteur;
import sn.smd.gestionbibliotheque.backend.service.AuteurService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auteurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuteurController {

    private final AuteurService auteurService;

    // =========================
    // GET ALL AUTEURS
    // =========================
    @GetMapping
    public ResponseEntity<List<Auteur>> getAll() {

        List<Auteur> list = auteurService.getAll();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(list);
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(auteurService.getById(id));
    }

    // =========================
    // UPDATE AUTEUR
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Auteur> update(@PathVariable Long id,
                                         @RequestBody Auteur auteur) {

        return ResponseEntity.ok(
                auteurService.update(id, auteur)
        );
    }

    // =========================
    // DELETE AUTEUR
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        auteurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // COUNT AUTEURS
    // =========================
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(auteurService.count());
    }
}