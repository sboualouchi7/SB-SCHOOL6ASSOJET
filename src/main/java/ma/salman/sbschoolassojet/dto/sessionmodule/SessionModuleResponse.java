package ma.salman.sbschoolassojet.dto.sessionmodule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionModuleResponse {
    private Long id;
    private Long sessionId;
    private Long moduleId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateAjout;
    private int ordre;
    private String nomSession;
    private String libelleModule;
}
