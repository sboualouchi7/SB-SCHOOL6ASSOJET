package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.enums.TypeEvaluation;
import ma.salman.sbschoolassojet.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEtudiantId(Long etudiantId);
    List<Evaluation> findByModuleId(Long moduleId);
    List<Evaluation> findByEnseignantId(Long enseignantId);
    List<Evaluation> findBySessionId(Long sessionId);
    List<Evaluation> findByType(TypeEvaluation type);
    List<Evaluation> findByEstValideeTrue();
    List<Evaluation> findByEstValideeFalse();

    @Query("SELECT AVG(e.note) FROM Evaluation e WHERE e.etudiantId = :etudiantId AND e.moduleId = :moduleId AND e.estValidee = true")
    Float findMoyenneByEtudiantIdAndModuleId(@Param("etudiantId") Long etudiantId, @Param("moduleId") Long moduleId);

    @Query("SELECT AVG(e.note) FROM Evaluation e WHERE e.moduleId = :moduleId AND e.sessionId = :sessionId AND e.estValidee = true")
    Float findMoyenneByModuleIdAndSessionId(@Param("moduleId") Long moduleId, @Param("sessionId") Long sessionId);
}
