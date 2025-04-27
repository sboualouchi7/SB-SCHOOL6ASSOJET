package ma.salman.sbschoolassojet.services;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.absence.AbsenceRequest;
import ma.salman.sbschoolassojet.dto.absence.AbsenceResponse;
import ma.salman.sbschoolassojet.dto.etudiant.EtudiantResponse;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.AbsenceMapper;
import ma.salman.sbschoolassojet.mappers.EtudiantMapper;
import ma.salman.sbschoolassojet.models.Absence;
import ma.salman.sbschoolassojet.models.Classe;
import ma.salman.sbschoolassojet.models.Etudiant;
import ma.salman.sbschoolassojet.models.Module;
import ma.salman.sbschoolassojet.models.Seance;
import ma.salman.sbschoolassojet.repositories.AbsenceRepository;
import ma.salman.sbschoolassojet.repositories.ClasseRepository;
import ma.salman.sbschoolassojet.repositories.EtudiantRepository;
import ma.salman.sbschoolassojet.repositories.ModuleRepository;
import ma.salman.sbschoolassojet.repositories.SeanceRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final SeanceRepository seanceRepository;
    private final ModuleRepository moduleRepository;
    private final ClasseRepository classeRepository;
    private final AbsenceMapper absenceMapper;
    private final EtudiantMapper etudiantMapper;

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

    public List<AbsenceResponse> getAbsencesByEtudiantAndPeriode(Long etudiantId, LocalDate dateDebut, LocalDate dateFin) {
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

    // Nouvelles méthodes pour le flux d'enregistrement des absences

    public List<EtudiantResponse> getEtudiantsByModuleClasseForEnseignant(Long moduleId, Long classeId, Long enseignantId) {
        // Vérifier que le module existe et est enseigné par cet enseignant
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'ID: " + moduleId));

        if (!module.getEnseignantId().equals(enseignantId)) {
            throw new AccessDeniedException("Vous n'êtes pas autorisé à accéder à ce module");
        }

        // Vérifier que la classe existe
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'ID: " + classeId));

        // Vérifier que le module est bien associé à cette classe ou à son niveau
        if (module.getClasseId() != null && !module.getClasseId().equals(classeId) &&
                !(module.getNiveauId() != null && module.getNiveauId().equals(classe.getNiveauId()))) {
            throw new IllegalArgumentException("Ce module n'est pas associé à cette classe");
        }

        // Récupérer les étudiants de la classe
        List<Etudiant> etudiants = etudiantRepository.findByClasseIdAndActifTrue(classeId);

        return etudiants.stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AbsenceResponse> createAbsencesBulk(List<AbsenceRequest> requests, Long enseignantId) {
        List<Absence> absences = new ArrayList<>();

        for (AbsenceRequest request : requests) {
            // Vérifier que la séance existe et est associée à cet enseignant
            Seance seance = seanceRepository.findById(request.getSeanceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Séance non trouvée avec l'ID: " + request.getSeanceId()));

            if (!seance.getEnseignantId().equals(enseignantId)) {
                throw new AccessDeniedException("Vous n'êtes pas autorisé à enregistrer des absences pour cette séance");
            }

            // Vérifier que l'étudiant existe
            Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'ID: " + request.getEtudiantId()));

            // Créer l'absence
            Absence absence = absenceMapper.toEntity(request);
            absences.add(absence);
        }

        // Sauvegarder toutes les absences
        List<Absence> savedAbsences = absenceRepository.saveAll(absences);

        // Convertir et retourner les réponses
        return savedAbsences.stream()
                .map(absenceMapper::toDto)
                .collect(Collectors.toList());
    }
}