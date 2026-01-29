# Exemples d'exécution et cas d'usage des tests

## 🚀 Démarrage rapide

### Installation des dépendances (si nécessaire)
```bash
# Le pom.xml inclut déjà spring-boot-starter-test
# Aucune installation supplémentaire requise
mvn clean install
```

### Exécution basique
```bash
# Exécuter tous les tests
mvn test

# Exécuter avec rapport détaillé
mvn test -v
```

---

## 📋 Exemples par type de test

### 1. Tests d'entité - EtudiantTest.java

#### Exécuter tous les tests de l'entité
```bash
mvn test -Dtest=EtudiantTest
```

**Résultat attendu** :
```
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123s
[INFO] BUILD SUCCESS
```

#### Tests individuels
```bash
# Constructeur par défaut
mvn test -Dtest=EtudiantTest#testEtudiantDefaultConstructor

# Constructeur complet
mvn test -Dtest=EtudiantTest#testEtudiantAllArgsConstructor

# Getters/Setters
mvn test -Dtest=EtudiantTest#testSetAndGetIdEtudiant
mvn test -Dtest=EtudiantTest#testSetAndGetNomEtudiant
mvn test -Dtest=EtudiantTest#testSetAndGetPrenomEtudiant
mvn test -Dtest=EtudiantTest#testSetAndGetOption

# Fonctionnalités
mvn test -Dtest=EtudiantTest#testEtudiantToString
mvn test -Dtest=EtudiantTest#testEtudiantIsSerializable
mvn test -Dtest=EtudiantTest#testOptionEnum
mvn test -Dtest=EtudiantTest#testEtudiantEquality
```

---

### 2. Tests d'énumération - OptionTest.java

#### Exécuter tous les tests d'Option
```bash
mvn test -Dtest=OptionTest
```

#### Tests spécifiques
```bash
# Valider chaque option
mvn test -Dtest=OptionTest#testTwinOption
mvn test -Dtest=OptionTest#testSaeOption
mvn test -Dtest=OptionTest#testDsOption

# Tester la conversion
mvn test -Dtest=OptionTest#testValueOfValidOption
mvn test -Dtest=OptionTest#testValueOfInvalidOption
```

**Cas de test - Option invalide** :
```bash
# Ce test doit FAIL (comportement attendu)
mvn test -Dtest=OptionTest#testValueOfInvalidOption
```

---

### 3. Tests de service - EtudiantServiceImplTest.java

#### Exécuter tous les tests du service
```bash
mvn test -Dtest=EtudiantServiceImplTest
```

#### Tests spécifiques avec mock
```bash
# Tests de lecture
mvn test -Dtest=EtudiantServiceImplTest#testAfficherEtudiants
mvn test -Dtest=EtudiantServiceImplTest#testAfficherEtudiants_EmptyList
mvn test -Dtest=EtudiantServiceImplTest#testAfficherEtudiantById_Found
mvn test -Dtest=EtudiantServiceImplTest#testAfficherEtudiantById_NotFound

# Tests de création
mvn test -Dtest=EtudiantServiceImplTest#testAjouterEtudiant
mvn test -Dtest=EtudiantServiceImplTest#testAjouterEtudiant_WithNull

# Tests de modification
mvn test -Dtest=EtudiantServiceImplTest#testModifierEtudiant

# Tests de suppression
mvn test -Dtest=EtudiantServiceImplTest#testSupprimerEtudiant
mvn test -Dtest=EtudiantServiceImplTest#testSupprimerEtudiant_InvalidId
```

**Exemple de sortie** :
```
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ crudEtudiant ---
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.456s - in tn.esprit.spring.crudetudiant.services.EtudiantServiceImplTest
```

---

### 4. Tests du contrôleur - EtudiantControllerTest.java

#### Exécuter tous les tests du contrôleur
```bash
mvn test -Dtest=EtudiantControllerTest
```

#### Tests par endpoint

**GET /afficherAllEtudiant**
```bash
mvn test -Dtest=EtudiantControllerTest#testAfficherAllEtudiant
mvn test -Dtest=EtudiantControllerTest#testAfficherAllEtudiant_EmptyList
mvn test -Dtest=EtudiantControllerTest#testAfficherAllEtudiant_ContentType
```

**GET /afficheById/{id}**
```bash
mvn test -Dtest=EtudiantControllerTest#testAfficherEtudiantByID
mvn test -Dtest=EtudiantControllerTest#testAfficherEtudiantByID_NotFound
```

**POST /ajouterEtudiant**
```bash
mvn test -Dtest=EtudiantControllerTest#testAjouterEtudiant
mvn test -Dtest=EtudiantControllerTest#testAjouterEtudiant_Complete
```

**PUT /modifierEtudiant**
```bash
mvn test -Dtest=EtudiantControllerTest#testModifierEtudiant
mvn test -Dtest=EtudiantControllerTest#testModifierEtudiant_ChangePrenom
```

**DELETE /supprimer/{id}**
```bash
mvn test -Dtest=EtudiantControllerTest#testSupprimerEtudiant
mvn test -Dtest=EtudiantControllerTest#testSupprimerEtudiant_InvalidId
```

---

### 5. Tests du repository - EtudiantRepositoryTest.java

#### Exécuter tous les tests du repository
```bash
mvn test -Dtest=EtudiantRepositoryTest
```

#### Tests CRUD
```bash
# CREATE
mvn test -Dtest=EtudiantRepositoryTest#testSaveEtudiant
mvn test -Dtest=EtudiantRepositoryTest#testSaveWithAllFields

# READ
mvn test -Dtest=EtudiantRepositoryTest#testFindAllEtudiants
mvn test -Dtest=EtudiantRepositoryTest#testFindByIdEtudiant
mvn test -Dtest=EtudiantRepositoryTest#testFindByIdNotFound

# UPDATE
mvn test -Dtest=EtudiantRepositoryTest#testUpdateEtudiant

# DELETE
mvn test -Dtest=EtudiantRepositoryTest#testDeleteById
mvn test -Dtest=EtudiantRepositoryTest#testDeleteEtudiant

# AUTRES
mvn test -Dtest=EtudiantRepositoryTest#testCountEtudiants
mvn test -Dtest=EtudiantRepositoryTest#testExistsById
mvn test -Dtest=EtudiantRepositoryTest#testExistsByIdNotFound
```

---

### 6. Tests d'intégration - CrudEtudiantApplicationTests.java

#### Exécuter tous les tests d'intégration
```bash
mvn test -Dtest=CrudEtudiantApplicationTests
```

#### Tests individuels
```bash
# Contexte Spring
mvn test -Dtest=CrudEtudiantApplicationTests#contextLoads

# Démarrage application
mvn test -Dtest=CrudEtudiantApplicationTests#applicationStartsSuccessfully

# Beans
mvn test -Dtest=CrudEtudiantApplicationTests#controllerBeanExists
mvn test -Dtest=CrudEtudiantApplicationTests#serviceBeanExists
mvn test -Dtest=CrudEtudiantApplicationTests#repositoryBeanExists
mvn test -Dtest=CrudEtudiantApplicationTests#allBeansShouldBeCreated

# Endpoints
mvn test -Dtest=CrudEtudiantApplicationTests#testEndpointAfficherAllEtudiant
```

---

## 🔄 Exécution en groupes

### Tests d'unité seulement (rapides)
```bash
# Exécuter : entités, énumération, service
mvn test -Dtest="EtudiantTest,OptionTest,EtudiantServiceImplTest"
```

### Tests d'intégration (plus lents)
```bash
# Exécuter : repository, contrôleur, application
mvn test -Dtest="EtudiantRepositoryTest,EtudiantControllerTest,CrudEtudiantApplicationTests"
```

### Tous les tests
```bash
mvn test
```

---

## 📊 Rapport de couverture

### Générer un rapport JaCoCo
```bash
# Installer et générer le rapport
mvn clean test jacoco:report

# Consulter le rapport
# Fichier : target/site/jacoco/index.html
```

### Couverture souhaitée
```
Package Couverture Cible : 100%
- Classes     : 100% (7/7)
- Méthodes    : 100%
- Lignes      : > 95%
```

---

## 🛠️ Options avancées de Maven

### Exécution parallèle (plus rapide)
```bash
mvn test -P parallel -DthreadCount=4
```

### Sans arrêt à la première erreur
```bash
mvn test -fn
```

### Mode verbeux (pour déboguer)
```bash
mvn test -X
```

### Skip tests (construction rapide)
```bash
mvn clean install -DskipTests
```

### Tests sur une branche spécifique
```bash
# Exécuter uniquement les tests modifiés
mvn test -Dtest=$(git diff --name-only origin/main | grep -o 'Test\.java')
```

---

## 🐛 Déboguer un test qui échoue

### Exécuter en mode debug
```bash
# Avec points d'arrêt dans l'IDE
mvn test -Dtest=EtudiantTest#testEtudiantDefaultConstructor -DdebugForkedProcess

# Depuis l'IDE
# Clic droit sur la classe → Debug
```

### Afficher plus de détails
```bash
# Afficher la sortie des tests
mvn test -Dtest=EtudiantTest -e

# Très verbeux
mvn test -Dtest=EtudiantTest -X -e
```

### Exécuter en isolation
```bash
# Un test à la fois pour déboguer
mvn test -Dtest=EtudiantTest#testEtudiantDefaultConstructor
```

---

## 📈 Exemple de résultat d'exécution complet

```bash
$ mvn clean test
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< tn.esprit.spring:crudEtudiant >------------------
[INFO] Building crudEtudiant 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ crudEtudiant ---
[INFO] Deleting C:\workspace\Devops\CrudEtudiant\target
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ crudEtudiant ---
[INFO] Using 'UTF-8' encoding to copy filtered resources
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ crudEtudiant ---
[INFO] Compiling 7 source files to target\classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-resources) @ crudEtudiant ---
[INFO] Using 'UTF-8' encoding to copy filtered resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-compile) @ crudEtudiant ---
[INFO] Compiling 6 test source files to target\test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ crudEtudiant ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running tn.esprit.spring.crudetudiant.entities.EtudiantTest
Tests pour l'entité Etudiant
  ✓ Créer un étudiant avec le constructeur vide
  ✓ Créer un étudiant avec le constructeur complet
  ✓ Tester le setter et getter pour l'ID
  ✓ Tester le setter et getter pour le nom
  ✓ Tester le setter et getter pour le prénom
  ✓ Tester le setter et getter pour l'option
  ✓ Tester le toString de l'entité Etudiant
  ✓ Tester la sérialisation de l'entité Etudiant
  ✓ Tester les différentes options
  ✓ Tester l'égalité entre deux étudiants avec les mêmes données
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.234 s

[INFO] Running tn.esprit.spring.crudetudiant.entities.OptionTest
Tests pour l'énumération Option
  ✓ Tester que TWIN est une option valide
  ✓ Tester que SAE est une option valide
  ✓ Tester que DS est une option valide
  ✓ Tester le nombre d'options
  ✓ Tester que toutes les options sont distinctes
  ✓ Tester values() retourne toutes les options
  ✓ Tester valueOf avec option valide
  ✓ Tester valueOf avec option invalide
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123 s

... (résultats des autres suites de tests)

[INFO] 
[INFO] -------------------------------------------------------
[INFO] T E S T S   S U M M A R Y
[INFO] -------------------------------------------------------
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.456s
[INFO] 
[INFO] BUILD SUCCESS
[INFO] 
[INFO] Total time:  5.234 s
[INFO] Finished at: 2026-01-29T10:30:45+01:00
```

---

## ✅ Checklist de test

Avant de commit :

- [ ] Tous les tests passent : `mvn test`
- [ ] Pas d'erreurs de compilation
- [ ] Couverture > 95%
- [ ] Pas de warnings
- [ ] Rapport JaCoCo consulté
- [ ] Logs propres (pas d'erreurs)

```bash
# Vérification complète
mvn clean test jacoco:report -DskipITs=false

# Check couverture
open target/site/jacoco/index.html
```

---

**Date** : 2026-01-29
**Projet** : CrudEtudiant
**Total tests** : 67 ✅

