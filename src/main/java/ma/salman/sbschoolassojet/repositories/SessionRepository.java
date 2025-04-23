package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.enums.StatutSession;
import ma.salman.sbschoolassojet.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByResponsableId(Long responsableId);
    List<Session> findByAnneeScolaire(String anneeScolaire);
    List<Session> findByStatut(StatutSession statut);
    List<Session> findByActifTrue();
}
