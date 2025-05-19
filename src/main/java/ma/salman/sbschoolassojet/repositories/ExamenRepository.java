package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Examen;
import ma.salman.sbschoolassojet.enums.TypeExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Integer> {

    List<Examen> findByClasseId(Long classeId);

    List<Examen> findByTypeExamen(TypeExamen typeExamen);

    List<Examen> findByDateExamenBetween(LocalDate debut, LocalDate fin);

    Optional<Examen> findByIdAndClasseId(Integer id, Long classeId);

    boolean existsByClasseIdAndTypeExamen(Long classeId, TypeExamen typeExamen);
}