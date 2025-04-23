package ma.salman.sbschoolassojet.repositories;


import ma.salman.sbschoolassojet.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}