package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.models.SessionModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionModuleRepository extends JpaRepository<SessionModule, Long> {
    List<SessionModule> findBySessionId(Long sessionId);
    List<SessionModule> findByModuleId(Long moduleId);
    List<SessionModule> findBySessionIdOrderByOrdreAsc(Long sessionId);
}
