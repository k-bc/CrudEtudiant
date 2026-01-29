# 🚀 Solutions rapides pour corriger les tests Jenkins

## ⚡ Solution rapide en 3 étapes

### Étape 1 : Vérifier le Jenkinsfile (déjà fait ✓)
Votre Jenkinsfile a été mis à jour avec une **étape TEST complète**. Vérifiez :

```bash
cat Jenkinsfile | grep -A 5 "stage('TEST')"
```

Vous devez voir :
```groovy
stage('TEST') {
    steps {
        echo '========== ÉTAPE TEST UNITAIRES =========='
        sh 'mvn test -q'
    }
}
```

### Étape 2 : Désactiver Drag & Drop dans VirtualBox

C'est **l'erreur principale**. Procédure :

1. **Arrêter la VM** Jenkins
2. **Ouvrir VirtualBox**
3. **Clic droit** sur la VM → **Paramètres**
4. **Onglet Avancé**
5. **Glisser-déposer** → Sélectionner **Désactivé**
6. **OK** et **Redémarrer la VM**

### Étape 3 : Relancer la pipeline Jenkins

1. Aller dans Jenkins
2. Cliquer sur votre job
3. Cliquer sur **Build Now**
4. Surveiller la console

---

## 🔍 Vérifier que les tests s'exécutent

### Dans la console Jenkins

Vous devez voir ces lignes :

```
========== ÉTAPE TEST UNITAIRES ==========
Exécution de 67 tests unitaires...

[INFO] Scanning for projects...
[INFO] Building crudEtudiant 0.0.1-SNAPSHOT
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ crudEtudiant ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
✓ Tests exécutés avec succès
```

### Si vous ne voyez pas cette ligne

**Vérifier** :
```bash
# SSH dans Jenkins
ssh -i key.pem user@jenkins-server

# Aller dans le workspace
cd /var/jenkins_home/workspace/CrudEtudiant

# Tester manuellement
mvn test -q

# Si erreur, voir les détails
mvn test
```

---

## 🛠️ Configuration Jenkins (si pas déjà fait)

### Option A : Pipeline (recommandé)

1. Nouveau job → **Pipeline**
2. Pipeline script from SCM
3. SCM : **Git**
4. Repository URL : `https://github.com/your-repo/CrudEtudiant.git`
5. Script path : **Jenkinsfile**
6. **Sauvegarder** et **Build Now**

### Option B : Free-style Job (classique)

1. Nouveau job → **Free-style job**
2. **Source Code Management** → Git
   - Repository URL : `https://github.com/your-repo/CrudEtudiant.git`
3. **Build** → Add build step → Execute shell

```bash
#!/bin/bash
set -e

echo "========== ÉTAPE CLEAN =========="
mvn clean

echo "========== ÉTAPE COMPILE =========="
mvn compile

echo "========== ÉTAPE TEST =========="
mvn test -q

echo "========== ÉTAPE RAPPORTS =========="
mvn jacoco:report

echo "✓ Build complété avec succès"
```

4. **Post-build Actions** :
   - Publish JUnit test result report
   - Test report XMLs : `target/surefire-reports/*.xml`
   - Publish HTML reports
   - HTML directory to archive : `target/site/jacoco`

5. **Sauvegarder** et **Build Now**

---

## 🚨 Les vrais problèmes (pas le DnD error)

Si les tests ne passent vraiment pas, vérifiez :

### 1. Maven n'est pas installé

```bash
# Sur le serveur Jenkins
which mvn

# Si pas trouvé, installer
sudo apt-get install maven  # Debian/Ubuntu
# ou
brew install maven  # macOS
```

### 2. Java n'est pas configuré

```bash
# Vérifier Java
java -version

# Vérifier JAVA_HOME
echo $JAVA_HOME

# Si pas configuré, ajouter au profil Jenkins
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
```

### 3. Git n'a pas accès au repository

```bash
# Vérifier les credentials Git
cd /var/jenkins_home/workspace/CrudEtudiant
git status

# Si erreur de credentials, configurer
git config --global credential.helper store
```

### 4. Permissions insuffisantes

```bash
# Vérifier les droits
ls -la /var/jenkins_home/workspace/CrudEtudiant

# Corriger si nécessaire
sudo chmod -R 755 /var/jenkins_home/workspace/CrudEtudiant
```

### 5. Espace disque insuffisant

```bash
# Vérifier l'espace
df -h

# Nettoyer si nécessaire
mvn clean
rm -rf ~/.m2/repository/  # Attention !
```

---

## ✅ Vérification finale

Lancez cette commande sur le serveur pour confirmer :

```bash
cd /var/jenkins_home/workspace/CrudEtudiant
mvn clean test -B -q
echo "Exit code: $?"
```

**Résultat attendu** :
```
Exit code: 0
```

(Code de sortie 0 = succès)

---

## 📊 Résultats attendus dans Jenkins

### Build réussi

```
Console Output :
========== ÉTAPE TEST UNITAIRES ==========
Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS

Status : SUCCESS (🟢 vert)
Test Report : 67 tests passed
Code Coverage : >95%
```

### Build échoué

```
Console Output :
Tests run: 67, Failures: 3, Errors: 0, Skipped: 0
[FAILURE] BUILD FAILURE

Status : FAILURE (🔴 rouge)
Détails dans : target/surefire-reports/
```

---

## 🎯 Si les tests échouent vraiment

1. **Voir les détails** :
   ```bash
   cd /var/jenkins_home/workspace/CrudEtudiant
   mvn test -e  # Mode verbeux
   ```

2. **Voir les rapports** :
   ```bash
   cat target/surefire-reports/TEST-*.xml | grep -i failure
   ```

3. **Recompiler les tests** :
   ```bash
   mvn test-compile -q
   ```

4. **Exécuter UN test** :
   ```bash
   mvn test -Dtest=EtudiantTest
   ```

---

## 💡 Conseils utiles

| Problème | Commande |
|----------|----------|
| Tests lents | `mvn test -T 1C` (paralléliser) |
| Test spécifique | `mvn test -Dtest=EtudiantTest` |
| Sans affichage | `mvn test -q` |
| Avec détails | `mvn test -X` |
| Rapport HTML | `mvn jacoco:report` |

---

## 🎉 Résumé

✅ **Jenkinsfile mis à jour** → Stage TEST ajoutée
✅ **Erreur VirtualBox DnD** → Solution : Désactiver DnD
✅ **Tests doivent s'exécuter** → `mvn test -q`
✅ **Résultats** → Publiés automatiquement dans Jenkins

**Prochaine action** : Relancer la pipeline Jenkins 🚀

---

## 📞 Support rapide

| Question | Réponse |
|----------|--------|
| Tests ne passent pas ? | Voir JENKINS_TROUBLESHOOTING.md |
| Comment configurer Jenkins ? | Voir JENKINS_CONFIGURATION_GUIDE.md |
| Comment exécuter localement ? | Voir DEMARRAGE_RAPIDE.md |
| Détails des tests ? | Voir TESTS_GUIDE.md |

---

**Date** : 29 janvier 2026
**Status** : ✅ PRÊT POUR JENKINS

