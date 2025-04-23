package ma.salman.sbschoolassojet.repositories;
import ma.salman.sbschoolassojet.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT p FROM Parent p JOIN p.enfants e WHERE e.id = :etudiantId")
    List<Parent> findByEtudiantId(@Param("etudiantId") Long etudiantId);
}