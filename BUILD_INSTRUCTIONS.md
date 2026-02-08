# Instructions de Build - Après Remédiation des CVEs

## Configuration Maven Requise

Maven 3.9.6 a été installé à l'emplacement suivant:
```
C:\tools\apache-maven-3.9.6
```

## Commandes de Build

### 1. Compilation Simple (Compile only)
```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean compile
```
**Temps estimé**: ~10 secondes

### 2. Compilation avec Tests
```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean test
```
**Temps estimé**: ~30 secondes (avec téléchargement des dépendances de test)

### 3. Vérification Complète (Compile + Tests + JaCoCo Coverage)
```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean verify
```
**Temps estimé**: ~30 secondes
**Inclut**:
- Compilation du source
- Exécution des tests
- Génération des rapports de couverture JaCoCo
- Vérification des seuils de couverture (85% pour les lignes, 80% pour les branches)

### 4. Génération du Paquet (JAR)
```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean package
```
**Résultat**: `target\crudEtudiant-0.0.1-SNAPSHOT.jar`

### 5. Mode Silencieux (sans verbosité)
Ajouter `-q` à toute commande pour réduire la sortie:
```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean compile -q
```

## Mise en Place d'un Alias Maven (Recommandé)

Pour simplifier les commandes, créez un alias PowerShell:

### Méthode 1: Session Actuelle
```powershell
Set-Alias mvn "C:\tools\apache-maven-3.9.6\bin\mvn.cmd"
```

Après cela, vous pouvez utiliser simplement:
```powershell
mvn clean compile
mvn clean verify
mvn clean package
```

### Méthode 2: Permanent (pour tous les futurs PowerShell)
1. Créer un profil PowerShell:
```powershell
if (!(Test-Path $PROFILE)) {
    New-Item -ItemType File -Path $PROFILE -Force
}
```

2. Ajouter l'alias au profil:
```powershell
Add-Content -Path $PROFILE -Value "Set-Alias mvn 'C:\tools\apache-maven-3.9.6\bin\mvn.cmd'"
```

3. Recharger le profil:
```powershell
. $PROFILE
```

## Variables d'Environnement Système (Optionnel)

Pour utiliser `mvn` de n'importe où dans le système:

### Sur Windows (GUI):
1. Ouvrir: `Variables d'environnement` (Win + Pause ou Panneau de configuration)
2. Créer une nouvelle variable:
   - **Nom**: `MAVEN_HOME`
   - **Valeur**: `C:\tools\apache-maven-3.9.6`
3. Modifier `Path` et ajouter: `%MAVEN_HOME%\bin`
4. Redémarrer PowerShell

### Via PowerShell (Administrateur):
```powershell
[Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\tools\apache-maven-3.9.6", "Machine")
$env:PATH = "$env:PATH;C:\tools\apache-maven-3.9.6\bin"
```

## Vérification de l'Installation

```powershell
C:\tools\apache-maven-3.9.6\bin\mvn.cmd -version
```

**Output attendu**:
```
Apache Maven 3.9.6
Maven home: C:\tools\apache-maven-3.9.6
Java version: 17.0.12
```

## Résolution des Problèmes

### Erreur: "mvn.cmd is not recognized"
**Solution**: Utilisez le chemin complet ou configurez les alias/variables d'environnement comme décrit ci-dessus

### Erreur: "Cannot find Java"
**Solution**: Vérifiez que Java 11+ est installé et que `JAVA_HOME` est correctement configuré

### Build échoue avec erreurs de dépendances
**Solution**: 
```powershell
# Nettoyer le cache local Maven
C:\tools\apache-maven-3.9.6\bin\mvn.cmd clean -U
# -U force la mise à jour des dépendances
```

## Changements Effectués

### Dépendances Mises à Jour:
1. **MySQL Connector/J**: 8.0.32 → 8.2.0 (CVE-2023-22102 fixé)
2. **H2 Database**: 2.1.214 → 2.2.220 (CVE-2022-45868 fixé)

### Fichier Modifié:
- `pom.xml`: Ajout des versions explicites pour MySQL Connector/J et H2

**Status**: ✅ Tous les CVEs de haute/critique sévérité ont été corrigés

---
**Date de dernière modification**: February 8, 2026

