package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantRequest;
import ma.salman.sbschoolassojet.dto.enseignant.EnseignantResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.EnseignantMapper;
import ma.salman.sbschoolassojet.models.Enseignant;
import ma.salman.sbschoolassojet.repositories.EnseignantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantService {
    private final EnseignantRepository enseignantRepository;
    private final EnseignantMapper enseignantMapper;
    private final PasswordEncoder passwordEncoder;

    public List<EnseignantResponse> getAllEnseignants() {
        return enseignantRepository.findAll().stream()
                .map(enseignantMapper::toDto)
                .collect(Collectors.toList());
    }

    public EnseignantResponse getEnseignantById(Long id) {
        return enseignantRepository.findById(id)
                .map(enseignantMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + id));
    }

    public List<EnseignantResponse> getEnseignantsByDepartement(Long departementId) {
        return enseignantRepository.findByDepartementId(departementId).stream()
                .map(enseignantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnseignantResponse createEnseignant(EnseignantRequest request) {
        Enseignant enseignant = enseignantMapper.toEntity(request);
        enseignant.setPassword(passwordEncoder.encode(request.getPassword()));
        enseignant.setRole(Role.ENSEIGNANT);
        return enseignantMapper.toDto(enseignantRepository.save(enseignant));
    }

    @Transactional
    public EnseignantResponse updateEnseignant(Long id, EnseignantRequest request) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + id));
        Role originalRole = enseignant.getRole();

        enseignantMapper.updateEntityFromDto(request, enseignant);
        enseignant.setRole(originalRole);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            enseignant.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return enseignantMapper.toDto(enseignantRepository.save(enseignant));
    }

    @Transactional
    public void deleteEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + id);
        }
        enseignantRepository.deleteById(id);
    }
}
