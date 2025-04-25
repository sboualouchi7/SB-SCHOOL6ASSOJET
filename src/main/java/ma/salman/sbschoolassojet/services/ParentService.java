package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.parent.ParentRequest;
import ma.salman.sbschoolassojet.dto.parent.ParentResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ParentMapper;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.models.Parent;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import ma.salman.sbschoolassojet.repositories.ParentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final EtudiantRepository etudiantRepository;
    private final ParentMapper parentMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(ParentService.class);

    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll().stream()
                .map(parentMapper::toDto)
                .collect(Collectors.toList());
    }

    public ParentResponse getParentById(Long id) {
        return parentRepository.findById(id)
                .map(parentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Parent non trouvé avec l'ID: " + id));
    }

    public List<ParentResponse> getParentsByEtudiantId(Long etudiantId) {
        return parentRepository.findByEtudiantId(etudiantId).stream()
                .map(parentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParentResponse createParent(ParentRequest request) {
        try {
            Parent parent = parentMapper.toEntity(request);
            parent.setPassword(passwordEncoder.encode(request.getPassword()));
            parent.setRole(Role.PARENT);
            if (parent.getEnfants() == null) {
                parent.setEnfants(new HashSet<>());
            }
            Parent savedParent = parentRepository.save(parent);

            if (request.getEnfantsIds() != null && !request.getEnfantsIds().isEmpty()) {
                for (Long etudiantId : request.getEnfantsIds()) {
                    try {
                        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + etudiantId));

                        logger.info("Étudiant trouvé: {} {}", etudiant.getId(), etudiant.getNom());

                        if (savedParent.getEnfants() == null) {
                            savedParent.setEnfants(new HashSet<>());
                        }

                        savedParent.getEnfants().add(etudiant);

                        if (etudiant.getParents() == null) {
                            etudiant.setParents(new HashSet<>());
                        }
                        etudiant.getParents().add(savedParent);
                        etudiantRepository.save(etudiant);
                    } catch (Exception e) {
                        throw e;
                    }
                }
                savedParent = parentRepository.save(savedParent);
            }

            return parentMapper.toDto(savedParent);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public ParentResponse updateParent(Long id, ParentRequest request) {
        try {
            Parent parent = parentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Parent non trouvé avec l'ID: " + id));

            Set<Etudiant> oldEnfants = new HashSet<>();
            if (parent.getEnfants() != null) {
                oldEnfants.addAll(parent.getEnfants());
            }
            Role originalRole = parent.getRole();
            parentMapper.updateEntityFromDto(request, parent);
            parent.setRole(originalRole);
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                parent.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            // S'assurer que la collection des enfants n'est pas null
            if (parent.getEnfants() == null) {
                parent.setEnfants(new HashSet<>());
            }
            // Mettre à jour la relation parent-étudiant
            if (request.getEnfantsIds() != null) {
                // Supprimer le parent des anciens étudiants qui ne sont plus associés
                for (Etudiant oldEnfant : oldEnfants) {
                    if (request.getEnfantsIds().stream().noneMatch(ids -> ids.equals(oldEnfant.getId()))) {
                        // S'assurer que la collection des parents n'est pas null
                        if (oldEnfant.getParents() != null) {
                            oldEnfant.getParents().remove(parent);
                            etudiantRepository.save(oldEnfant);
                        }
                    }
                }
                // Vider la collection pour la reconstruire
                parent.getEnfants().clear();

                // Ajouter les nouveaux étudiants
                for (Long etudiantId : request.getEnfantsIds()) {
                    Etudiant etudiant = etudiantRepository.findById(etudiantId)
                            .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + etudiantId));
                    // Ajouter l'étudiant au parent
                    parent.getEnfants().add(etudiant);

                    // S'assurer que la collection des parents n'est pas null
                    if (etudiant.getParents() == null) {
                        etudiant.setParents(new HashSet<>());
                    }
                    // Ajouter le parent à l'étudiant (relation bidirectionnelle)
                    etudiant.getParents().add(parent);

                    etudiantRepository.save(etudiant);
                }
            }

            return parentMapper.toDto(parentRepository.save(parent));
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du parent: {}", id, e);
            throw e;
        }
    }

    @Transactional
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parent non trouvé avec l'ID: " + id);
        }
        parentRepository.deleteById(id);
    }
}
