package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByNiveauId(Long niveauId);
    List<Classe> findByAnneeScolaire(String anneeScolaire);
    List<Classe> findByNiveauIdAndActifTrue(Long niveauId);
    List<Classe> findByAnneeScolaireAndActifTrue(String anneeScolaire);
}
