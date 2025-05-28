SB-SCHOOL-ASSOJET
Description du Projet
SB-SCHOOL-ASSOJET est un système de gestion scolaire complet développé avec Spring Boot. Cette application permet de gérer les étudiants, enseignants, parents, classes, modules, évaluations, absences, documents administratifs et plus encore.
Fonctionnalités Principales

Gestion des utilisateurs (Admin, Enseignant, Étudiant, Parent)
Gestion des classes et des niveaux scolaires
Gestion des modules et des enseignements
Suivi des présences et des absences
Système d'évaluation et de notation
Demandes et gestion de documents administratifs
Planification des examens
Authentification sécurisée avec JWT

Prérequis

Java 17 ou supérieur
Maven 3.6+ (ou utiliser le Maven Wrapper inclus)
PostgreSQL 12+ (ou H2 pour le développement)
Un IDE comme IntelliJ IDEA, Eclipse ou VS Code
(c'est mieux d'utiliser Intellij pour eviter les problemes de configuration)

Installation et Configuration
1. Cloner le projet
bashgit clone [[URL_DU_REPOSITORY]](https://github.com/sboualouchi7/SB-SCHOOL6ASSOJET)
cd SB-SCHOOL-ASSOJET

3. Configuration de la base de données
Créez une base de données PostgreSQL pour le projet :

sqlCREATE DATABASE sb_school_assojet;

4. Configuration de l'application

Modifiez le fichier src/main/resources/application.properties ou application.yml pour configurer la connexion à la base de données et d'autres paramètres :
properties# Configuration de la base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/sb_school_assojet (le nom de database que vous avez deja crrer en postgres)
spring.datasource.username=schooluser (nom d'utilisateur par defaut c'est postgres)
spring.datasource.password=password (ton mot de passe de postgres)
spring.jpa.hibernate.ddl-auto=update

# Configuration JWT
app.jwtSecret=votreCléSecrèteJWT
app.jwtExpirationMs=86400000

# Configuration du serveur
server.port=8080

4. Compilation et exécution
Utilisez le Maven Wrapper pour compiler et exécuter l'application :

bash# Sous Linux/Mac
./mvnw spring-boot:run

# Sous Windows
mvnw.cmd spring-boot:run
Ou avec Maven si vous l'avez installé :
bashmvn spring-boot:run
Structure du Projet
src/main/java/ma/salman/sbschoolassojet/
├── Config/                # Configurations Spring Boot
├── controllers/           # Contrôleurs REST API
├── dto/                   # Objets de transfert de données
├── enums/                 # Énumérations
├── exceptions/            # Gestionnaires d'exceptions
├── mappers/               # Mappeurs entre entités et DTOs
├── models/                # Entités JPA
├── repositories/          # Interfaces de repository JPA
├── security/              # Configuration de sécurité et JWT
├── services/              # Services métier
└── SbSchoolAssojetApplication.java  # Point d'entrée de l'application

Documentation de l'API
Une fois l'application démarrée, vous pouvez accéder à la documentation Swagger de l'API à l'adresse :
http://localhost:8080/swagger-ui/index.html
Cette interface vous permettra d'explorer et de tester toutes les endpoints de l'API.
Utilisateur par défaut
Le système crée automatiquement un utilisateur administrateur par défaut lors du premier démarrage :

Nom d'utilisateur : test5
Mot de passe : test
Rôle : ADMIN

Ce comportement peut être modifié dans la classe DataInitializerConfig.java.
Endpoints principaux

Authentification : /api/auth/login
Étudiants : /api/etudiants
Enseignants : /api/enseignants
Parents : /api/parents
Classes : /api/classes
Modules : /api/modules
Évaluations : /api/evaluations
Absences : /api/absences
Documents : /api/documents
Sessions : /api/sessions
Niveaux : /api/niveaux

Sécurité
L'application utilise Spring Security avec JWT (JSON Web Tokens) pour l'authentification. Toutes les requêtes aux endpoints protégés doivent inclure un token JWT valide dans l'en-tête Authorization :
Authorization: Bearer [votre_token_jwt]
Le token JWT est obtenu en s'authentifiant via l'endpoint /api/auth/login.
Support et Contact
Pour toute question ou assistance concernant ce projet, veuillez contacter :

Salman : salmanboualouchi@gmail.com

Licence
Ce projet est distribué sous licence Apache License 2.0.
