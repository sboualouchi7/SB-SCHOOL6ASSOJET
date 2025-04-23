package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    List<Niveau> findByActifTrue();
    List<Niveau> findByOrderByOrdreAsc();
}
