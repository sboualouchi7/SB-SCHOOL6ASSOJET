package ma.salman.sbschoolassojet.repositories;

import ma.salman.sbschoolassojet.enums.StatusDocument;
import ma.salman.sbschoolassojet.enums.TypeDocument;
import ma.salman.sbschoolassojet.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByEtudiantId(Long etudiantId);
    List<Document> findByDemandeurId(Long demandeurId);
    List<Document> findByType(TypeDocument type);
    List<Document> findByStatus(StatusDocument status);
    List<Document> findByEtudiantIdAndActifTrue(Long etudiantId);
}
