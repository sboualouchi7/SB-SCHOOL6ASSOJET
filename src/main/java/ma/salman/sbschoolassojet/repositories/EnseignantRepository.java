package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Optional<Enseignant> findByNumeroCarte(String numeroCarte);
    List<Enseignant> findByDepartementId(Long departementId);
}