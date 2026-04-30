package sn.smd.gestionbibliotheque.backend.service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.smd.gestionbibliotheque.backend.entity.Role;
import sn.smd.gestionbibliotheque.backend.entity.TypeDeRole;
import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;
import sn.smd.gestionbibliotheque.backend.entity.Validation;
import sn.smd.gestionbibliotheque.backend.exceptions.ConflictExceptions;
import sn.smd.gestionbibliotheque.backend.exceptions.NotFoundExceptions;
import sn.smd.gestionbibliotheque.backend.repository.RoleRepository;
import sn.smd.gestionbibliotheque.backend.repository.UtilisateurRepository;
import sn.smd.gestionbibliotheque.backend.repository.ValidationRepository;
import sn.smd.gestionbibliotheque.backend.service.UtilisateurService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;
    private final ValidationRepository validationRepository;

    @Override
    public Utilisateur create(Utilisateur utilisateur) {

        if (!utilisateur.getEmail().contains("@") || !utilisateur.getEmail().contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }

        Optional<Utilisateur> utilisateurOptional =
                utilisateurRepository.findByEmail(utilisateur.getEmail());

        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre email est déjà utilisé");
        }

        String encodedPassword =
                bCryptPasswordEncoder.encode(utilisateur.getPassword());

        utilisateur.setPassword(encodedPassword);

        Role roleUtilisateur = (Role) roleRepository.findByLibelle(TypeDeRole.USER)
                .orElseThrow(() -> new RuntimeException("Role USER introuvable"));

        utilisateur.setRole(roleUtilisateur);
        utilisateur.setActif(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        validationService.enregistrerValidation(utilisateur);

        return utilisateur;
    }

    @Override
    public Utilisateur update(Long id, Utilisateur user) {

        Utilisateur existing = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Utilisateur introuvable"));

        existing.setNom(user.getNom());
        existing.setPrenom(user.getPrenom());
        existing.setSexe(user.getSexe());

        return utilisateurRepository.save(existing);
    }

    @Override
    public void delete(Long id) {

        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Utilisateur introuvable"));

        utilisateurRepository.delete(user);
    }

    @Override
    public Utilisateur getById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Utilisateur introuvable"));
    }

    @Override
    public Utilisateur getByUsername(String username) {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundExceptions("Username introuvable"));
    }

    @Override
    public List<Utilisateur> getAll(Boolean actif) {

        if (actif == null) {
            return utilisateurRepository.findAll();
        }

        return utilisateurRepository.findAll()
                .stream()
//                .filter(u -> u.isActif() == actif)
                .toList();
    }

    @Override
    public Utilisateur toggleActive(Long id) {

        Utilisateur user = getById(id);
//        user.setActif(!user.isActif());

        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur activate(Map<String, String> activation) {

        Validation validation = this.validationService
                .lireEnFonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }

        Utilisateur utilisateurActiver = validation.getUtilisateur();

        utilisateurActiver.setActif(true);

        // 🔥 Marquer comme activé
        validation.setActivation(Instant.now());

        this.utilisateurRepository.save(utilisateurActiver);
        this.validationRepository.save(validation);

        return utilisateurActiver;
    }

    @Override
    public long count() {
        return utilisateurRepository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

    }
}