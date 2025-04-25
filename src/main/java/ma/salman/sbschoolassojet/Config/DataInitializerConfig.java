package ma.salman.sbschoolassojet.Config;

import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.enums.Sexe;
import ma.salman.sbschoolassojet.models.Utilisateur;
import ma.salman.sbschoolassojet.repositories.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

@Configuration
public class DataInitializerConfig {

    //@Bean
    public CommandLineRunner initializeUsers(UtilisateurRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Démarrage de l'initialisation des utilisateurs...");

            // Vérifier si l'utilisateur existe déjà
            if (!userRepository.existsByUsername("test")) {
                Utilisateur user = new Utilisateur();
                user.setUsername("test");
                user.setPassword(passwordEncoder.encode("test"));
                user.setEmail("test@example.com");
                user.setNom("Test");
                user.setPrenom("User");
                user.setRole(Role.ADMIN);
                user.setSexe(Sexe.MASCULIN);
                user.setActifAccount(true);
                user.setDateCreation(LocalDate.now());
                user.setDateModification(LocalDate.now());

                Utilisateur savedUser = userRepository.save(user);

                System.out.println("Utilisateur 'test' créé avec succès! ID: " + savedUser.getId());
                System.out.println("Username: " + savedUser.getUsername());
                System.out.println("Password encodé: " +
                        (savedUser.getPassword() != null && savedUser.getPassword().length() >= 10
                                ? savedUser.getPassword().substring(0, 10) + "..."
                                : savedUser.getPassword()));                System.out.println("Role: " + savedUser.getRole());
            } else {
                System.out.println("L'utilisateur 'test' existe déjà.");
                Utilisateur existingUser = userRepository.findByUsername("test").orElse(null);
                if (existingUser != null) {
                    System.out.println("Infos de l'utilisateur existant:");
                    System.out.println("ID: " + existingUser.getId());
                    System.out.println("Username: " + existingUser.getUsername());
                    System.out.println("Password défini: " + (existingUser.getPassword() != null));
                    System.out.println("Role: " + existingUser.getRole());
                }
            }
        };
    }
}