package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEtudiantId(Long etudiantId);
    List<Absence> findBySeanceId(Long seanceId);
    List<Absence> findByValideeTrue();
    List<Absence> findByValideeFalse();

    @Query("SELECT a FROM Absence a WHERE a.etudiantId = :etudiantId AND a.dateDebut >= :dateDebut AND a.dateFin <= :dateFin")
    List<Absence> findByEtudiantIdAndPeriode(@Param("etudiantId") Long etudiantId,
                                             @Param("dateDebut") Date dateDebut,
                                             @Param("dateFin") Date dateFin);
}
