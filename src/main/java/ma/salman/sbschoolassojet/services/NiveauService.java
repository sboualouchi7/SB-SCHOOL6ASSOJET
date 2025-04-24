package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.niveau.NiveauRequest;
import ma.salman.sbschoolassojet.dto.niveau.NiveauResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.NiveauMapper;
import ma.salman.sbschoolassojet.models.Niveau;
import ma.salman.sbschoolassojet.repositories.NiveauRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NiveauService {
    private final NiveauRepository niveauRepository;
    private final NiveauMapper niveauMapper;

    public List<NiveauResponse> getAllNiveaux() {
        return niveauRepository.findAll().stream()
                .map(niveauMapper::toDto)
                .collect(Collectors.toList());
    }

    public NiveauResponse getNiveauById(Long id) {
        return niveauRepository.findById(id)
                .map(niveauMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + id));
    }

    public List<NiveauResponse> getNiveauxActifs() {
        return niveauRepository.findByActifTrue().stream()
                .map(niveauMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NiveauResponse> getNiveauxOrdonnes() {
        return niveauRepository.findByOrderByOrdreAsc().stream()
                .map(niveauMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public NiveauResponse createNiveau(NiveauRequest request) {
        Niveau niveau = niveauMapper.toEntity(request);
        return niveauMapper.toDto(niveauRepository.save(niveau));
    }

    @Transactional
    public NiveauResponse updateNiveau(Long id, NiveauRequest request) {
        Niveau niveau = niveauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + id));

        niveauMapper.updateEntityFromDto(request, niveau);
        return niveauMapper.toDto(niveauRepository.save(niveau));
    }

    @Transactional
    public void deleteNiveau(Long id) {
        if (!niveauRepository.existsById(id)) {
            throw new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + id);
        }
        niveauRepository.deleteById(id);
    }

    @Transactional
    public NiveauResponse activerNiveau(Long id, boolean actif) {
        Niveau niveau = niveauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'ID: " + id));

        niveau.setActif(actif);
        return niveauMapper.toDto(niveauRepository.save(niveau));
    }
}
