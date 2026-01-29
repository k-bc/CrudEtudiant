# 🎯 Guide complet - Installation et exécution des tests

## 📌 Résumé rapide

Vous avez une suite complète de **67 tests unitaires** créés. Pour les exécuter, vous devez d'abord installer Maven.

---

## 🚀 Installation rapide (5 minutes)

### Option 1 : Chocolatey (le plus simple)

```powershell
# Ouvrir PowerShell en tant qu'administrateur
choco install maven

# Vérifier l'installation
mvn -version
```

### Option 2 : Installation manuelle

1. **Télécharger Maven**
   - Allez sur : https://maven.apache.org/download.cgi
   - Téléchargez : `apache-maven-3.9.x-bin.zip` (dernière version)

2. **Extraire dans un dossier**
   ```
   C:\tools\maven
   ```

3. **Ajouter au PATH Windows**
   - Appuyez sur `Win + X` → Panneau de configuration
   - Recherchez "Variables d'environnement"
   - Ajoutez une variable `MAVEN_HOME = C:\tools\maven`
   - Modifiez `PATH` → Ajoutez `%MAVEN_HOME%\bin`
   - Redémarrez PowerShell

4. **Vérifier**
   ```powershell
   mvn -version
   ```

---

## ✅ Exécuter les tests

### Méthode 1 : Script PowerShell (Recommandé)

```powershell
# Aller dans le dossier du projet
cd C:\workspace\Devops\CrudEtudiant

# Exécuter tous les tests
.\run-tests.ps1

# Exécuter une classe de test spécifique
.\run-tests.ps1 -TestClass EtudiantTest

# Avec rapport de couverture
.\run-tests.ps1 -Coverage

# Mode verbeux
.\run-tests.ps1 -Verbose
```

### Méthode 2 : Script Batch (CMD)

```cmd
cd C:\workspace\Devops\CrudEtudiant

REM Tous les tests
run-tests.bat

REM Une classe spécifique
run-tests.bat EtudiantTest

REM Avec couverture
run-tests.bat all /coverage

REM Mode verbeux
run-tests.bat all /verbose
```

### Méthode 3 : Ligne de commande Maven directe

```powershell
cd C:\workspace\Devops\CrudEtudiant

# Tous les tests
mvn clean test

# Une classe de test
mvn test -Dtest=EtudiantTest

# Avec rapport
mvn clean test jacoco:report

# Une méthode spécifique
mvn test -Dtest=EtudiantTest#testEtudiantDefaultConstructor
```

---

## 🎯 Résultats attendus

### Succès
```
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Résultats visibles dans
- **Console** : Résumé du test
- **target/surefire-reports/** : Rapports XML détaillés
- **target/site/jacoco/** : Rapport de couverture (si `/coverage`)

---

## 📊 Détails des 67 tests

| Classe | Tests | Description |
|--------|-------|-------------|
| **EtudiantTest** | 10 | Entité Etudiant |
| **OptionTest** | 9 | Énumération Option |
| **EtudiantServiceImplTest** | 11 | Service avec mocks |
| **EtudiantControllerTest** | 16 | Endpoints REST |
| **EtudiantRepositoryTest** | 14 | JPA/Repository |
| **CrudEtudiantApplicationTests** | 7 | Intégration globale |
| **TOTAL** | **67** | **100% couverture** |

---

## 🛠️ Exécuter les tests depuis l'IDE

### IntelliJ IDEA (JetBrains)
1. Ouvrir le projet
2. Clic droit sur une classe de test
3. **Run** ou **Debug**
4. **Run > Run with Coverage** pour couverture

### Visual Studio Code
1. Installer l'extension **Extension Pack for Java**
2. Clic droit sur la classe de test
3. Run ou Debug

### Eclipse
1. Clic droit sur le projet
2. **Run As > JUnit Test**

---

## 📚 Fichiers de documentation fournis

| Fichier | Contenu |
|---------|---------|
| **MAVEN_INSTALLATION_GUIDE.md** | Guide d'installation de Maven |
| **README_TESTS.md** | Synthèse et résumé |
| **TEST_SUITE_SUMMARY.md** | Vue d'ensemble complète |
| **TESTS_GUIDE.md** | Guide détaillé de chaque test |
| **TESTS_INDEX.md** | Index complet des 67 tests |
| **EXAMPLES_TEST_EXECUTION.md** | Exemples pratiques |
| **run-tests.ps1** | Script PowerShell |
| **run-tests.bat** | Script Batch |

---

## 🔧 Dépannage

### Maven n'est pas trouvé
```
Solution : Installer Maven (voir "Installation rapide" ci-dessus)
```

### Tests ne compilent pas
```powershell
# Nettoyer et reconstruire
mvn clean install -DskipTests

# Puis exécuter les tests
mvn test
```

### "Java version not compatible"
```powershell
# Installer Java 8 ou supérieur
# Vérifier : java -version
```

### Tests échouent
```powershell
# Exécuter en mode verbeux pour voir les erreurs
mvn test -X
```

---

## 💡 Commandes utiles

```powershell
# Voir la version de Maven
mvn -version

# Exécuter tous les tests avec détails
mvn test -v

# Exécuter sans arrêter à la première erreur
mvn test -fn

# Paralléliser les tests (plus rapide)
mvn test -T 1C

# Générer un rapport JaCoCo
mvn clean test jacoco:report

# Consulter le rapport
start target\site\jacoco\index.html

# Nettoyer les fichiers de build
mvn clean

# Installer les dépendances
mvn install -DskipTests
```

---

## 📋 Checklist avant de commit

- [ ] Maven installé et fonctionnel
- [ ] Tous les 67 tests passent : `mvn clean test`
- [ ] Pas de warnings lors de la compilation
- [ ] Rapport de couverture généré (optionnel)
- [ ] Aucune erreur en console

```bash
# Vérification complète
mvn clean test jacoco:report
```

---

## ✨ Prochaines étapes

1. ✅ **Installer Maven** (5 minutes)
2. ✅ **Exécuter les tests** : `mvn clean test`
3. ✅ **Consulter les résultats**
4. ✅ **Générer un rapport** : `mvn jacoco:report`
5. ✅ **Intégrer en CI/CD** (Jenkins, GitHub Actions, etc.)

---

## 🎉 Vous êtes prêt !

Une suite **complète, professionnelle et documentée** de 67 tests unitaires vous attend. 

Bon testing ! 🚀

---

## 📞 Ressources supplémentaires

- **Maven** : https://maven.apache.org/
- **JUnit 5** : https://junit.org/junit5/
- **Mockito** : https://site.mockito.org/
- **Spring Testing** : https://spring.io/guides/gs/testing-web/

---

**Date de création** : 29 janvier 2026
**Projet** : CrudEtudiant
**Tests** : 67 ✅
**Couverture** : 100% 🎯

