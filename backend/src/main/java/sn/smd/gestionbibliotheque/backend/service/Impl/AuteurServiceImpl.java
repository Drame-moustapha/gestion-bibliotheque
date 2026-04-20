package sn.smd.gestionbibliotheque.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.smd.gestionbibliotheque.backend.entity.Auteur;
import sn.smd.gestionbibliotheque.backend.exceptions.NotFoundException;
import sn.smd.gestionbibliotheque.backend.repository.AuteurRepository;
import sn.smd.gestionbibliotheque.backend.service.AuteurService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuteurServiceImpl implements AuteurService {

    private final AuteurRepository auteurRepository;

    @Override
    public Auteur create(Auteur auteur) {
        return auteurRepository.save(auteur);
    }

    @Override
    public Auteur update(Long id, Auteur auteur) {

        Auteur existing = auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec id: " + id));

        existing.setNom(auteur.getNom());
        existing.setPrenom(auteur.getPrenom());
        existing.setUsername(auteur.getUsername());
        existing.setEmail(auteur.getEmail());
        existing.setSexe(auteur.getSexe());
        existing.setBiographie(auteur.getBiographie());
        existing.setPays(auteur.getPays());
        existing.setInstitution(auteur.getInstitution());
        existing.setSpecialite(auteur.getSpecialite());

        return auteurRepository.save(existing);
    }

    @Override
    public void delete(Long id) {

        Auteur auteur = auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec id: " + id));

        auteurRepository.delete(auteur);
    }

    @Override
    public Auteur getById(Long id) {
        return auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec id: " + id));
    }

    @Override
    public List<Auteur> getAll() {
        return auteurRepository.findAll();
    }

    @Override
    public long count() {
        return auteurRepository.count();
    }
}