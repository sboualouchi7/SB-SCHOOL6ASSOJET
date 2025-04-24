package ma.salman.sbschoolassojet.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@Getter
@Setter

@SuperBuilder
public class Admin extends Utilisateur{
    public Admin() {
        super();
    }
}
