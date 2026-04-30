package sn.smd.gestionbibliotheque.backend.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.smd.gestionbibliotheque.backend.entity.Auteur;
import sn.smd.gestionbibliotheque.backend.entity.Manuscrit;
import sn.smd.gestionbibliotheque.backend.exceptions.NotFoundExceptions;
import sn.smd.gestionbibliotheque.backend.repository.AuteurRepository;
import sn.smd.gestionbibliotheque.backend.repository.ManuscritRepository;
import sn.smd.gestionbibliotheque.backend.service.ManuscritService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManuscritServiceImpl implements ManuscritService {

    private final ManuscritRepository manuscritRepository;
    private final AuteurRepository auteurRepository;

    @Override
    public Manuscrit create(Manuscrit m) {

        Long auteurId = m.getAuteur().getId();

        Auteur auteur = auteurRepository.findById(auteurId)
                .orElseThrow(() -> new NotFoundExceptions("Auteur non existant"));

        m.setAuteur(auteur);

        return manuscritRepository.save(m);
    }

    @Override
    public Manuscrit update(Long id, Manuscrit m) {

        Manuscrit existing = manuscritRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Manuscrit introuvable"));

        existing.setTitre(m.getTitre());
        existing.setDescription(m.getDescription());
        existing.setContenu(m.getContenu());
        existing.setType(m.getType());
        existing.setPrix(m.getPrix());
        existing.setPayant(m.isPayant());
        existing.setLangue(m.getLangue());
        existing.setResume(m.getResume());

        return manuscritRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Manuscrit m = manuscritRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Manuscrit introuvable"));

        manuscritRepository.delete(m);
    }

    @Override
    public Manuscrit getById(Long id) {
        return manuscritRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Manuscrit introuvable"));
    }

    @Override
    public List<Manuscrit> getAll() {
        return manuscritRepository.findAll();
    }

    @Override
    public List<Manuscrit> getByAuteur(Long auteurId) {
        return manuscritRepository.findAllByAuteurId(auteurId);
    }

    @Override
    public List<Manuscrit> getPayants() {
        return List.of();
    }

    @Override
    public List<Manuscrit> searchByTitre(String keyword) {
        return List.of();
    }

    @Override
    public long count() {
        return manuscritRepository.count();
    }
}