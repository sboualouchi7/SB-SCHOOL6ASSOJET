package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.parent.ParentRequest;
import ma.salman.sbschoolassojet.dto.parent.ParentResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.ParentMapper;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.models.Parent;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import ma.salman.sbschoolassojet.repositories.ParentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final EtudiantRepository etudiantRepository;
    private final ParentMapper parentMapper;
    private final PasswordEncoder passwordEncoder;

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
        Parent parent = parentMapper.toEntity(request);
        parent.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getEnfantsIds() != null && !request.getEnfantsIds().isEmpty()) {
            parent.setEnfants(new HashSet<>());
            for (Long etudiantId : request.getEnfantsIds()) {
                Etudiant etudiant = etudiantRepository.findById(etudiantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + etudiantId));
                parent.getEnfants().add(etudiant);
            }
        }

        return parentMapper.toDto(parentRepository.save(parent));
    }

    @Transactional
    public ParentResponse updateParent(Long id, ParentRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent non trouvé avec l'ID: " + id));

        parentMapper.updateEntityFromDto(request, parent);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            parent.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getEnfantsIds() != null) {
            parent.getEnfants().clear();
            for (Long etudiantId : request.getEnfantsIds()) {
                Etudiant etudiant = etudiantRepository.findById(etudiantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + etudiantId));
                parent.getEnfants().add(etudiant);
            }
        }

        return parentMapper.toDto(parentRepository.save(parent));
    }

    @Transactional
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parent non trouvé avec l'ID: " + id);
        }
        parentRepository.deleteById(id);
    }
}
