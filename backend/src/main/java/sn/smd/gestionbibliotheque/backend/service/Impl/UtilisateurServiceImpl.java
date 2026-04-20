package sn.smd.gestionbibliotheque.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.smd.gestionbibliotheque.backend.entity.Role;
import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;
import sn.smd.gestionbibliotheque.backend.exceptions.ConflictException;
import sn.smd.gestionbibliotheque.backend.exceptions.NotFoundException;
import sn.smd.gestionbibliotheque.backend.repository.RoleRepository;
import sn.smd.gestionbibliotheque.backend.repository.UtilisateurRepository;
import sn.smd.gestionbibliotheque.backend.service.UtilisateurService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur create(Utilisateur user) {

        if (utilisateurRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("Username déjà utilisé");
        }

        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Rôle USER introuvable"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActif(true);
        user.setRoles(List.of(roleUser));

        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur update(Long id, Utilisateur user) {

        Utilisateur existing = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        existing.setNom(user.getNom());
        existing.setPrenom(user.getPrenom());
        existing.setSexe(user.getSexe());

        return utilisateurRepository.save(existing);
    }

    @Override
    public void delete(Long id) {

        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        utilisateurRepository.delete(user);
    }

    @Override
    public Utilisateur getById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
    }

    @Override
    public Utilisateur getByUsername(String username) {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username introuvable"));
    }

    @Override
    public List<Utilisateur> getAll(Boolean actif) {

        if (actif == null) {
            return utilisateurRepository.findAll();
        }

        return utilisateurRepository.findAll()
                .stream()
                .filter(u -> u.isActif() == actif)
                .toList();
    }

    @Override
    public Utilisateur toggleActive(Long id) {

        Utilisateur user = getById(id);
        user.setActif(!user.isActif());

        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur activate(Long id) {

        Utilisateur user = getById(id);
        user.setActif(true);

        return utilisateurRepository.save(user);
    }

    @Override
    public long count() {
        return utilisateurRepository.count();
    }
}