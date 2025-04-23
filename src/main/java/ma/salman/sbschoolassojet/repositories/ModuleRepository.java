package ma.salman.sbschoolassojet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ma.salman.sbschoolassojet.models.Module;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByClasseId(Long classeId);
    List<Module> findByEnseignantId(Long enseignantId);
    List<Module> findByClasseIdAndActifTrue(Long classeId);
    List<Module> findByEnseignantIdAndActifTrue(Long enseignantId);
}