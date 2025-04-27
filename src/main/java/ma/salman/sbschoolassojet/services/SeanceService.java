package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.seance.SeanceRequest;
import ma.salman.sbschoolassojet.dto.seance.SeanceResponse;
import ma.salman.sbschoolassojet.enums.StatusSeance;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.SeanceMapper;
import ma.salman.sbschoolassojet.models.Enseignant;
import ma.salman.sbschoolassojet.models.Seance;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.repositories.EnseignantRepository;
import ma.salman.sbschoolassojet.repositories.ModuleRepository;
import ma.salman.sbschoolassojet.repositories.SeanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final ModuleRepository moduleRepository;
    private final EnseignantRepository enseignantRepository;
    private final SeanceMapper seanceMapper;

    public List<SeanceResponse> getAllSeances() {
        return seanceRepository.findAll().stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public SeanceResponse getSeanceById(Long id) {
        return seanceRepository.findById(id)
                .map(seanceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + id));
    }

    public List<SeanceResponse> getSeancesByModule(Long moduleId) {
        return seanceRepository.findByModuleId(moduleId).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeanceResponse> getSeancesByEnseignant(Long enseignantId) {
        return seanceRepository.findByEnseignantId(enseignantId).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeanceResponse> getSeancesByStatut(StatusSeance statut) {
        return seanceRepository.findByStatut(statut).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeanceResponse> getSeancesByDate(LocalDate date) {
        return seanceRepository.findByDate(date).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeanceResponse> getSeancesByEnseignantAndPeriode(Long enseignantId, LocalDate dateDebut, LocalDate dateFin) {
        return seanceRepository.findByEnseignantIdAndPeriode(enseignantId, dateDebut, dateFin).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeanceResponse> getSeancesByClasseAndDate(Long classeId, LocalDate date) {
        return seanceRepository.findByClasseIdAndDate(classeId, date).stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SeanceResponse createSeance(SeanceRequest request) {
        Seance seance = seanceMapper.toEntity(request);

        // Vérifier que le module existe
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));

        // Vérifier que l'enseignant existe
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));
        seance.setModule(module);
        seance.setEnseignant(enseignant);

        return seanceMapper.toDto(seanceRepository.save(seance));
    }

    @Transactional
    public SeanceResponse updateSeance(Long id, SeanceRequest request) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + id));

        seanceMapper.updateEntityFromDto(request, seance);

        // Vérifier que le module existe s'il est modifié
        if (request.getModuleId() != null && !request.getModuleId().equals(seance.getModuleId())) {
            Module module = moduleRepository.findById(request.getModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + request.getModuleId()));
        }

        // Vérifier que l'enseignant existe s'il est modifié
        if (request.getEnseignantId() != null && !request.getEnseignantId().equals(seance.getEnseignantId())) {
            Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + request.getEnseignantId()));
        }

        return seanceMapper.toDto(seanceRepository.save(seance));
    }

    @Transactional
    public SeanceResponse updateStatut(Long id, StatusSeance statut) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + id));

        seance.setStatut(statut);
        return seanceMapper.toDto(seanceRepository.save(seance));
    }

    @Transactional
    public void deleteSeance(Long id) {
        if (!seanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Séance non trouvée avec l'ID: " + id);
        }
        seanceRepository.deleteById(id);
    }

    @Transactional
    public SeanceResponse activerSeance(Long id, boolean actif) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + id));

        seance.setActif(actif);
        return seanceMapper.toDto(seanceRepository.save(seance));
    }
}
