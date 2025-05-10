package ma.salman.sbschoolassojet.dto.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurPhotoRequest {
    private MultipartFile photo;
}
