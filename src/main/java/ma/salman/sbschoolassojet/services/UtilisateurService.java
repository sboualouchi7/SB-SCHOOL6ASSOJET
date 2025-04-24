package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurRequest;
import ma.salman.sbschoolassojet.dto.utilisateur.UtilisateurResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.UtilisateurMapper;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    public UtilisateurResponse getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .map(utilisateurMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
    }

    @Transactional
    public UtilisateurResponse createUtilisateur(UtilisateurRequest request) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public UtilisateurResponse updateUtilisateur(Long id, UtilisateurRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        utilisateurMapper.updateEntityFromDto(request, utilisateur);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return utilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Transactional
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    public List<UtilisateurResponse> getUtilisateursByRole(Role role) {
        return utilisateurRepository.findByRole(role).stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return utilisateurRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return utilisateurRepository.existsByEmail(email);
    }
}
