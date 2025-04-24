package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.absence.AbsenceRequest;
import ma.salman.sbschoolassojet.dto.absence.AbsenceResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.AbsenceMapper;
import ma.salman.sbschoolassojet.models.Absence;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.models.Seance;
import ma.salman.sbschoolassojet.repositories.AbsenceRepository;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import ma.salman.sbschoolassojet.repositories.SeanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final SeanceRepository seanceRepository;
    private final AbsenceMapper absenceMapper;

    public List<AbsenceResponse> getAllAbsences() {
        return absenceRepository.findAll().stream()
                .map(absenceMapper::toDto)
                .collect(Collectors.toList());
    }

    public AbsenceResponse getAbsenceById(Long id) {
        return absenceRepository.findById(id)
                .map(absenceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Absence non trouvée avec l'ID: " + id));
    }

    public List<AbsenceResponse> getAbsencesByEtudiant(Long etudiantId) {
        return absenceRepository.findByEtudiantId(etudiantId).stream()
                .map(absenceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AbsenceResponse> getAbsencesBySeance(Long seanceId) {
        return absenceRepository.findBySeanceId(seanceId).stream()
                .map(absenceMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AbsenceResponse> getAbsencesByEtudiantAndPeriode(Long etudiantId, Date dateDebut, Date dateFin) {
        return absenceRepository.findByEtudiantIdAndPeriode(etudiantId, dateDebut, dateFin).stream()
                .map(absenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AbsenceResponse createAbsence(AbsenceRequest request) {
        Absence absence = absenceMapper.toEntity(request);

        // Vérifier que l'étudiant existe
        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));

        // Vérifier que la séance existe
        Seance seance = seanceRepository.findById(request.getSeanceId())
                .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + request.getSeanceId()));

        return absenceMapper.toDto(absenceRepository.save(absence));
    }

    @Transactional
    public AbsenceResponse updateAbsence(Long id, AbsenceRequest request) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence non trouvée avec l'ID: " + id));

        absenceMapper.updateEntityFromDto(request, absence);

        // Vérifier que l'étudiant existe s'il est modifié
        if (request.getEtudiantId() != null && !request.getEtudiantId().equals(absence.getEtudiantId())) {
            Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant non trouvé avec l'ID: " + request.getEtudiantId()));
        }

        // Vérifier que la séance existe si elle est modifiée
        if (request.getSeanceId() != null && !request.getSeanceId().equals(absence.getSeanceId())) {
            Seance seance = seanceRepository.findById(request.getSeanceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + request.getSeanceId()));
        }

        return absenceMapper.toDto(absenceRepository.save(absence));
    }

    @Transactional
    public AbsenceResponse validerAbsence(Long id, boolean validee) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence non trouvée avec l'ID: " + id));

        absence.setValidee(validee);
        return absenceMapper.toDto(absenceRepository.save(absence));
    }

    @Transactional
    public void deleteAbsence(Long id) {
        if (!absenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Absence non trouvée avec l'ID: " + id);
        }
        absenceRepository.deleteById(id);
    }
}
