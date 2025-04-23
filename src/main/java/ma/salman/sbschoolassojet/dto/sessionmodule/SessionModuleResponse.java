package ma.salman.sbschoolassojet.dto.sessionmodule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionModuleResponse {
    private Long id;
    private Long sessionId;
    private Long moduleId;
    private Date dateAjout;
    private int ordre;
    private String nomSession;
    private String libelleModule;
}
