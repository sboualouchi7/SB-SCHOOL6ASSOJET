package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long>, JpaSpecificationExecutor<Classe> {
    List<Classe> findByNiveauId(Long niveauId);
    List<Classe> findByAnneeScolaire(String anneeScolaire);
    List<Classe> findByNiveauIdAndActifTrue(Long niveauId);
    /**
     * Trouve toutes les classes associées à un enseignant via les modules qu'il enseigne
     * @param enseignantId L'ID de l'enseignant
     * @return Liste des classes où l'enseignant donne des cours
     */
    @Query("SELECT DISTINCT c FROM Classe c JOIN c.modules m WHERE m.enseignantId = :enseignantId")
    List<Classe> findByEnseignantId(@Param("enseignantId") Long enseignantId);
    List<Classe> findByAnneeScolaireAndActifTrue(String anneeScolaire);
}
