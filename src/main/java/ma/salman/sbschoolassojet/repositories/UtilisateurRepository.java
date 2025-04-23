package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<Utilisateur> findByRole(Role role);
}
