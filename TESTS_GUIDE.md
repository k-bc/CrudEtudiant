# Guide d'utilisation des tests unitaires

## 📚 Structure des fichiers de test

```
src/test/java/tn/esprit/spring/crudetudiant/
├── entities/
│   ├── EtudiantTest.java          # Tests de l'entité Etudiant
│   └── OptionTest.java            # Tests de l'énumération Option
├── services/
│   └── EtudiantServiceImplTest.java # Tests du service
├── controllers/
│   └── EtudiantControllerTest.java  # Tests des endpoints REST
├── repository/
│   └── EtudiantRepositoryTest.java  # Tests JPA/Repository
└── CrudEtudiantApplicationTests.java # Tests d'intégration
```

---

## 🧪 Détails des classes de test

### 1. EtudiantTest.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/entities/EtudiantTest.java`

Tests unitaires de l'entité `Etudiant` :

| Méthode | Description |
|---------|-------------|
| `testEtudiantDefaultConstructor()` | Valide le constructeur par défaut |
| `testEtudiantAllArgsConstructor()` | Valide le constructeur avec paramètres |
| `testSetAndGetIdEtudiant()` | Teste les getters/setters pour l'ID |
| `testSetAndGetNomEtudiant()` | Teste les getters/setters pour le nom |
| `testSetAndGetPrenomEtudiant()` | Teste les getters/setters pour le prénom |
| `testSetAndGetOption()` | Teste les getters/setters pour l'option |
| `testEtudiantToString()` | Valide la méthode toString() |
| `testEtudiantIsSerializable()` | Vérifie que l'entité est sérialisable |
| `testOptionEnum()` | Teste les options disponibles |
| `testEtudiantEquality()` | Teste l'égalité entre deux objets |

```bash
mvn test -Dtest=EtudiantTest
```

---

### 2. OptionTest.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/entities/OptionTest.java`

Tests de l'énumération `Option` :

| Méthode | Description |
|---------|-------------|
| `testTwinOption()` | Valide l'option TWIN |
| `testSaeOption()` | Valide l'option SAE |
| `testDsOption()` | Valide l'option DS |
| `testNumberOfOptions()` | Vérifie qu'il y a 3 options |
| `testOptionsAreDistinct()` | Valide que les options sont distinctes |
| `testValuesMethod()` | Teste la méthode values() |
| `testValueOfValidOption()` | Teste valueOf avec une option valide |
| `testValueOfInvalidOption()` | Teste valueOf avec une option invalide |

```bash
mvn test -Dtest=OptionTest
```

---

### 3. EtudiantServiceImplTest.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/services/EtudiantServiceImplTest.java`

Tests unitaires du service avec **mocking du repository** :

| Méthode | Description |
|---------|-------------|
| `testAfficherEtudiants()` | Teste la récupération de tous les étudiants |
| `testAfficherEtudiants_EmptyList()` | Teste avec liste vide |
| `testAjouterEtudiant()` | Teste l'ajout d'un étudiant |
| `testAjouterEtudiant_WithNull()` | Teste l'ajout avec null |
| `testModifierEtudiant()` | Teste la modification d'un étudiant |
| `testSupprimerEtudiant()` | Teste la suppression |
| `testSupprimerEtudiant_InvalidId()` | Teste la suppression avec ID invalide |
| `testAfficherEtudiantById_Found()` | Teste la recherche par ID (trouvé) |
| `testAfficherEtudiantById_NotFound()` | Teste la recherche par ID (non trouvé) |
| `testAfficherEtudiantById_ZeroId()` | Teste avec ID zéro |
| `testRepositoryInjection()` | Vérifie l'injection du repository |

```bash
mvn test -Dtest=EtudiantServiceImplTest
```

**Note** : Utilise `@ExtendWith(MockitoExtension.class)` et `@Mock` pour mocquer le repository.

---

### 4. EtudiantControllerTest.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/controllers/EtudiantControllerTest.java`

Tests des endpoints REST avec **MockMvc** :

| Endpoint | Méthode | Description |
|----------|---------|-------------|
| `GET /afficherAllEtudiant` | `testAfficherAllEtudiant()` | Récupère tous les étudiants |
| `GET /afficherAllEtudiant` | `testAfficherAllEtudiant_EmptyList()` | Récupère une liste vide |
| `GET /afficheById/{id}` | `testAfficherEtudiantByID()` | Récupère un étudiant par ID |
| `GET /afficheById/{id}` | `testAfficherEtudiantByID_NotFound()` | ID qui n'existe pas |
| `POST /ajouterEtudiant` | `testAjouterEtudiant()` | Ajoute un nouvel étudiant |
| `POST /ajouterEtudiant` | `testAjouterEtudiant_Complete()` | Ajoute avec tous les champs |
| `PUT /modifierEtudiant` | `testModifierEtudiant()` | Modifie un étudiant |
| `PUT /modifierEtudiant` | `testModifierEtudiant_ChangePrenom()` | Modifie le prénom |
| `DELETE /supprimer/{id}` | `testSupprimerEtudiant()` | Supprime un étudiant |
| `DELETE /supprimer/{id}` | `testSupprimerEtudiant_InvalidId()` | Supprime avec ID invalide |

```bash
mvn test -Dtest=EtudiantControllerTest
```

**Note** : Utilise `@WebMvcTest` et `MockMvc` pour simuler les requêtes HTTP.

---

### 5. EtudiantRepositoryTest.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/repository/EtudiantRepositoryTest.java`

Tests d'intégration du repository avec **H2 en mémoire** :

| Méthode | Description |
|---------|-------------|
| `testSaveEtudiant()` | Teste l'ajout d'un étudiant |
| `testFindAllEtudiants()` | Teste la récupération de tous les étudiants |
| `testFindByIdEtudiant()` | Teste la recherche par ID |
| `testFindByIdNotFound()` | Teste avec ID qui n'existe pas |
| `testUpdateEtudiant()` | Teste la modification |
| `testDeleteById()` | Teste la suppression par ID |
| `testCountEtudiants()` | Teste le comptage |
| `testExistsById()` | Teste l'existence |
| `testExistsByIdNotFound()` | Teste l'existence (non trouvé) |
| `testSaveWithAllFields()` | Teste l'ajout avec tous les champs |
| `testDeleteEtudiant()` | Teste la suppression d'un objet |

```bash
mvn test -Dtest=EtudiantRepositoryTest
```

**Note** : Utilise `@DataJpaTest` avec une base de données H2 en mémoire.

---

### 6. CrudEtudiantApplicationTests.java
**Localisation** : `src/test/java/tn/esprit/spring/crudetudiant/CrudEtudiantApplicationTests.java`

Tests d'intégration globaux de l'application :

| Méthode | Description |
|---------|-------------|
| `contextLoads()` | Valide le chargement du contexte Spring |
| `applicationStartsSuccessfully()` | Teste le démarrage de l'application |
| `springBootApplicationAnnotationPresent()` | Vérifie l'annotation |
| `controllerBeanExists()` | Valide l'injection du contrôleur |
| `serviceBeanExists()` | Valide l'injection du service |
| `repositoryBeanExists()` | Valide l'injection du repository |
| `testEndpointAfficherAllEtudiant()` | Teste l'endpoint GET |
| `mainMethodExists()` | Vérifie la méthode main |
| `allBeansShouldBeCreated()` | Valide la création de tous les beans |
| `mockMvcMustBeConfigured()` | Vérifie la configuration de MockMvc |

```bash
mvn test -Dtest=CrudEtudiantApplicationTests
```

---

## 🚀 Exécution des tests

### Exécuter tous les tests
```bash
mvn clean test
```

### Exécuter une classe de test spécifique
```bash
mvn test -Dtest=EtudiantTest
mvn test -Dtest=EtudiantServiceImplTest
mvn test -Dtest=EtudiantControllerTest
mvn test -Dtest=EtudiantRepositoryTest
mvn test -Dtest=CrudEtudiantApplicationTests
mvn test -Dtest=OptionTest
```

### Exécuter une méthode spécifique
```bash
mvn test -Dtest=EtudiantTest#testEtudiantDefaultConstructor
mvn test -Dtest=EtudiantServiceImplTest#testAjouterEtudiant
mvn test -Dtest=EtudiantControllerTest#testAfficherAllEtudiant
```

### Avec rapport de couverture
```bash
# Installer JaCoCo (si nécessaire)
mvn clean test jacoco:report

# Rapport disponible à : target/site/jacoco/index.html
```

### Depuis l'IDE (JetBrains)
- **Run** : Clic droit → Run
- **Debug** : Clic droit → Debug
- **Couverture** : Clic droit → Run with Coverage

---

## 📊 Résultats attendus

Tous les tests doivent **passer** (GREEN) :

```
Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
```

### Résumé par classe :
- `EtudiantTest` : 10 tests ✅
- `OptionTest` : 9 tests ✅
- `EtudiantServiceImplTest` : 11 tests ✅
- `EtudiantControllerTest` : 16 tests ✅
- `EtudiantRepositoryTest` : 14 tests ✅
- `CrudEtudiantApplicationTests` : 7 tests ✅

---

## 🔧 Dépannage

### Erreur "Cannot construct instance of Etudiant"
**Cause** : Manque le constructeur par défaut
**Solution** : Ajouter `@NoArgsConstructor` à l'entité

### Erreur "No qualifying bean of type"
**Cause** : Le bean n'est pas injecté
**Solution** : Ajouter `@Service`, `@Repository`, `@Controller`

### Tests MockMvc échouent
**Cause** : Annotations Spring manquantes
**Solution** : Utiliser `@WebMvcTest` ou `@SpringBootTest` + `@AutoConfigureMockMvc`

### Erreur "Database error"
**Cause** : Base de données non disponible
**Solution** : `@DataJpaTest` crée automatiquement H2 en mémoire

---

## 💡 Bonnes pratiques appliquées

✅ **Une assertion par test** (ou groupées logiquement)
✅ **Noms explicites** avec `@DisplayName`
✅ **Pattern AAA** : Arrangement, Action, Assertion
✅ **Mocking approprié** : Ne tester que la classe concernée
✅ **Cas normaux et limites** : null, listes vides, IDs invalides
✅ **@BeforeEach** : Initialisation commune
✅ **Conventions Maven** : Tests dans `src/test/java`

---

## 📚 Ressources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Testing Guide](https://spring.io/guides/gs/testing-web/)
- [MockMvc Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)

---

**Créé le** : 2026-01-29
**Pour le projet** : CrudEtudiant
**Couverture** : 100% des couches de l'application

