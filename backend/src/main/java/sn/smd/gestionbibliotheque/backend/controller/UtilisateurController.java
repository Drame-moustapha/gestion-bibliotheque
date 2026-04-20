package sn.smd.gestionbibliotheque.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.smd.gestionbibliotheque.backend.entity.Auteur;
import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;
import sn.smd.gestionbibliotheque.backend.model.Auteurs;
import sn.smd.gestionbibliotheque.backend.payload.ChangePasswordRequest;
import sn.smd.gestionbibliotheque.backend.service.UtilisateurService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // à sécuriser en production via config
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    // =========================
    // CREATE USER
    // =========================
    @PostMapping
    public ResponseEntity<Utilisateur> createUser(@RequestBody Utilisateur user) {
        return new ResponseEntity<>(
                utilisateurService.createUser(user),
                HttpStatus.CREATED
        );
    }

    // =========================
    // CREATE AUTEUR
    // =========================
    @PostMapping("/auteur")
    public ResponseEntity<Auteur> createAuteur(@RequestBody Auteurs user) {
        return new ResponseEntity<>(
                utilisateurService.createAuteur(user),
                HttpStatus.CREATED
        );
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUserById(id));
    }

    // =========================
    // GET BY USERNAME
    // =========================
    @GetMapping("/username/{username}")
    public ResponseEntity<Utilisateur> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(utilisateurService.getByUsername(username));
    }

    // =========================
    // GET ALL USERS
    // =========================
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll(
            @RequestParam(required = false) Boolean actif) {

        List<Utilisateur> users = utilisateurService.getAllsUser(actif);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    // =========================
    // UPDATE USER
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id,
                                                  @RequestBody Utilisateur user) {

        return ResponseEntity.ok(utilisateurService.updateUser(user, id));
    }

    // =========================
    // DELETE BY ID (REST CORRECT)
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        utilisateurService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // COUNT USERS
    // =========================
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(utilisateurService.countUtilisateur());
    }

    // =========================
    // ACTIVER / DESACTIVER COMPTE
    // =========================
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Utilisateur> toggleStatus(@PathVariable Long id) {
        Utilisateur user = utilisateurService.getUserById(id);
        return ResponseEntity.ok(
                utilisateurService.activeOuDesactiveCompte(user)
        );
    }

    // =========================
    // CHANGE PASSWORD (SECURITY)
    // =========================
    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(
                utilisateurService.changePassword(request)
        );
    }
}