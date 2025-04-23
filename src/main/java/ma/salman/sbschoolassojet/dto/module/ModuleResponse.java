package ma.salman.sbschoolassojet.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.salman.sbschoolassojet.enums.TypeModule;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private Long id;
    private String libelle;
    private int volumeHoraire;
    private int seuil;
    private float coefficient;
    private Long classeId;
    private Long niveauId;
    private Long enseignantId;
    private boolean actif;
    private String description;
    private TypeModule typeModule;
    private Date dateCreation;
    private Date dateModification;
    private String nomEnseignant;
    private String nomClasse;
}
