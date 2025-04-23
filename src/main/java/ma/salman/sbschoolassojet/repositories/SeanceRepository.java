package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.enums.StatusSeance;
import ma.salman.sbschoolassojet.models.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByModuleId(Long moduleId);
    List<Seance> findByEnseignantId(Long enseignantId);
    List<Seance> findByStatut(StatusSeance statut);
    List<Seance> findByDate(Date date);
    List<Seance> findByModuleIdAndActifTrue(Long moduleId);
    List<Seance> findByEnseignantIdAndActifTrue(Long enseignantId);

    @Query("SELECT s FROM Seance s WHERE s.enseignantId = :enseignantId AND s.date BETWEEN :dateDebut AND :dateFin")
    List<Seance> findByEnseignantIdAndPeriode(@ Param("enseignantId") Long enseignantId, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("SELECT s FROM Seance s JOIN s.module m JOIN m.classe c WHERE c.id = :classeId AND s.date = :date")
    List<Seance> findByClasseIdAndDate(@Param("classeId") Long classeId, @Param("date") Date date);
}
