# 🚀 Guide d'installation et d'exécution des tests

## ⚠️ Problème identifié

Maven n'est pas installé ou n'est pas dans le PATH de votre système.

---

## 📦 Solution 1 : Installer Maven manuellement

### Étapes d'installation sur Windows

#### 1. Télécharger Maven
- Allez sur : https://maven.apache.org/download.cgi
- Téléchargez la version binaire (ZIP) : `apache-maven-3.x.x-bin.zip`

#### 2. Extraire l'archive
```bash
# Extraire dans un dossier (exemple: C:\tools\maven)
# Créer le dossier s'il n'existe pas
```

#### 3. Ajouter Maven au PATH
```powershell
# 1. Ouvrir Variables d'environnement (Windows)
#    - Appuyez sur Win + Pause
#    - Cliquez sur "Variables d'environnement"
#    - Ou : Panneau de configuration > Système > Paramètres avancés du système

# 2. Ajouter une nouvelle variable :
#    Variable : MAVEN_HOME
#    Valeur : C:\tools\maven

# 3. Modifier le PATH :
#    Ajouter : %MAVEN_HOME%\bin

# 4. Redémarrer PowerShell/CMD

# 5. Vérifier l'installation
mvn -version
```

---

## 📦 Solution 2 : Utiliser Maven Wrapper (Recommandé)

Si Maven n'est pas encore configuré sur votre machine, créez le Maven Wrapper dans le projet :

### Étapes

#### 1. Générer le Maven Wrapper
```powershell
cd C:\workspace\Devops\CrudEtudiant

# Si Maven est disponible :
mvn wrapper:wrapper

# Sinon, voir Solution 1
```

#### 2. Vérifier que les fichiers ont été créés
```powershell
Get-ChildItem -Path "C:\workspace\Devops\CrudEtudiant" -Filter "mvnw*"
```

#### 3. Exécuter les tests avec le wrapper
```powershell
# Windows PowerShell
.\mvnw.cmd clean test

# Ou directement
.\mvnw.cmd clean test -q
```

---

## 🚀 Solution 3 : Installation rapide avec Chocolatey (Windows)

Si vous avez Chocolatey installé :

```powershell
# Installer Maven avec Chocolatey
choco install maven

# Vérifier
mvn -version
```

---

## 📋 Vérification de l'installation

```powershell
# Vérifier que Maven est installé
mvn -version

# Résultat attendu :
# Apache Maven 3.x.x (...)
# Maven home: C:\tools\maven
# Java version: 1.8.x
```

---

## ✅ Exécuter les tests une fois Maven installé

### Une fois Maven disponible :

```powershell
cd C:\workspace\Devops\CrudEtudiant

# Exécuter tous les tests
mvn clean test

# Exécuter une classe de test spécifique
mvn test -Dtest=EtudiantTest

# Exécuter avec rapport de couverture
mvn clean test jacoco:report
```

---

## 🎯 Résultat attendu

```
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< tn.esprit.spring:crudEtudiant >------------------
[INFO] Building crudEtudiant 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ crudEtudiant ---
[INFO] Deleting C:\workspace\Devops\CrudEtudiant\target
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ crudEtudiant ---
[INFO] Compiling 7 source files to target\classes
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-compile) @ crudEtudiant ---
[INFO] Compiling 6 test source files to target\test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ crudEtudiant ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.456s

[INFO] BUILD SUCCESS
```

---

## 🐛 Dépannage

### Erreur : "Java version not compatible"
```powershell
# Vérifier la version de Java
java -version

# Le projet nécessite Java 1.8 ou supérieur
# Installer Java si nécessaire depuis : https://www.oracle.com/java/technologies/downloads/
```

### Erreur : "Could not find or load main class"
```powershell
# Supprimer le cache et reconstruire
mvn clean install -DskipTests
```

### Tests ne compilent pas
```powershell
# Vérifier que les fichiers de test existent
dir src\test\java\tn\esprit\spring\crudetudiant\

# Compiler explicitement les tests
mvn test-compile
```

---

## 💡 Alternative : Exécution depuis l'IDE

Si vous utilisez IntelliJ IDEA (JetBrains) :

1. **Ouvrir le projet** dans IntelliJ
2. **Clic droit** sur une classe de test
3. **Run** ou **Run with Coverage**
4. **Ou** : Menu Run > Run 'NomTest'

L'IDE téléchargera automatiquement Maven s'il n'est pas présent.

---

## ✨ Prochaines étapes

1. ✅ **Installer Maven** (Solution 1, 2 ou 3)
2. ✅ **Vérifier** : `mvn -version`
3. ✅ **Exécuter les tests** : `mvn clean test`
4. ✅ **Consulter les résultats** dans le dossier `target`

---

**Note** : Une fois Maven installé, vous pouvez exécuter tous les tests avec la commande :
```powershell
mvn clean test
```

Cela exécutera les **67 tests** créés et vous donnera un rapport détaillé. 🎉

