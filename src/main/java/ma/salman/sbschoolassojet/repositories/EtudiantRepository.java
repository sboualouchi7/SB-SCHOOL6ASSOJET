package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    Optional<Etudiant> findByNumeroEtudiant(String numeroEtudiant);
    List<Etudiant> findByClasseId(Long classeId);
    List<Etudiant> findByNiveauId(Long niveauId);
    List<Etudiant> findByAnneeScolaire(String anneeScolaire);
    List<Etudiant> findByClasseIdAndActifTrue(Long classeId);
}