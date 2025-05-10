package ma.salman.sbschoolassojet.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "sbschoolassojet",
                        "resource_type", "auto"
                )
        );

        return (String) uploadResult.get("secure_url");
    }

    public void deleteImage(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            // Extraire le public_id à partir de l'URL
            if (publicId.contains("/")) {
                String[] parts = publicId.split("/");
                String fileName = parts[parts.length - 1];
                // Supprimer l'extension de fichier si présente
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                publicId = "sbschoolassojet/" + fileName;
            }

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
    }
}