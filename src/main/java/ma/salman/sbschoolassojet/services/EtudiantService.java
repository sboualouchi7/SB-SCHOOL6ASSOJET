package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantRequest;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.EtudiantMapper;
import ma.salman.sbschoolassojet.models.Classe;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.repositories.ClasseRepository;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {
    private final EtudiantRepository etudiantRepository;
    private final ClasseRepository classeRepository;
    private final EtudiantMapper etudiantMapper;
    private final PasswordEncoder passwordEncoder;

    public List<EtudiantResponse> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    public EtudiantResponse getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(etudiantMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + id));
    }

    public List<EtudiantResponse> getEtudiantsByClasse(Long classeId) {
        return etudiantRepository.findByClasseId(classeId).stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EtudiantResponse> getEtudiantsByNiveau(Long niveauId) {
        return etudiantRepository.findByNiveauId(niveauId).stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EtudiantResponse> getEtudiantsByAnneeScolaire(String anneeScolaire) {
        return etudiantRepository.findByAnneeScolaire(anneeScolaire).stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EtudiantResponse createEtudiant(EtudiantRequest request) {
        Etudiant etudiant = etudiantMapper.toEntity(request);
        etudiant.setPassword(passwordEncoder.encode(request.getPassword()));
        etudiant.setRole(Role.ETUDIANT);
        // Vérifier que la classe existe
        Classe classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + request.getClasseId()));

        return etudiantMapper.toDto(etudiantRepository.save(etudiant));
    }

    @Transactional
    public EtudiantResponse updateEtudiant(Long id, EtudiantRequest request) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + id));
        Role originalRole = etudiant.getRole();
        etudiantMapper.updateEntityFromDto(request, etudiant);
        etudiant.setRole(originalRole);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            etudiant.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Vérifier que la classe existe si elle est modifiée
        if (request.getClasseId() != null && !request.getClasseId().equals(etudiant.getClasseId())) {
            Classe classe = classeRepository.findById(request.getClasseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + request.getClasseId()));
        }

        return etudiantMapper.toDto(etudiantRepository.save(etudiant));
    }

    @Transactional
    public void deleteEtudiant(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + id);
        }
        etudiantRepository.deleteById(id);
    }

    @Transactional
    public EtudiantResponse activerEtudiant(Long id, boolean actif) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + id));

        etudiant.setActif(actif);
        return etudiantMapper.toDto(etudiantRepository.save(etudiant));
    }
}
