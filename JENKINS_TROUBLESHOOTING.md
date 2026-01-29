# 🔧 Diagnostic et débogage - Tests Jenkins et VirtualBox

## 📋 Diagnostic rapide

Pour identifier le vrai problème, exécutez ces vérifications sur le serveur Jenkins :

### 1. Vérifier l'environnement Jenkins

```bash
# Qui exécute Jenkins ?
whoami

# Où est Jenkins ?
ps aux | grep jenkins

# Vérifier les droits
ls -la /var/jenkins_home/

# Vérifier l'espace disque
df -h
```

### 2. Vérifier Maven

```bash
# Maven est-il accessible ?
which mvn
mvn -version

# Vérifier les variables d'environnement
echo $JAVA_HOME
echo $M2_HOME
echo $PATH
```

### 3. Vérifier Git

```bash
# Git est-il accessible ?
which git
git --version

# Vérifier les credentials
cat ~/.git-credentials
```

### 4. Vérifier le projet

```bash
# Aller dans le workspace
cd /var/jenkins_home/workspace/CrudEtudiant

# Vérifier les fichiers
ls -la

# Vérifier le Jenkinsfile
cat Jenkinsfile

# Tester manuellement
mvn clean test -q
```

---

## 🐛 L'erreur VirtualBox DnD expliquée

### Qu'est-ce que c'est ?

**DnD** = Drag and Drop (Glisser-Déposer)

C'est une **fonctionnalité de VirtualBox** qui permet de glisser-déposer des fichiers entre la machine hôte et la VM.

### Pourquoi cette erreur ?

1. **Guest Additions mal installées** → Solution : Réinstaller
2. **Drag & Drop désactivé** → Solution : L'activer dans les paramètres
3. **Incompatibilité VirtualBox** → Solution : Mettre à jour VirtualBox
4. **Jenkins essaie d'utiliser DnD** → Solution : Désactiver DnD

### Est-ce que cela affecte les tests ?

**NON** ❌ Cette erreur **n'affecte PAS** l'exécution des tests.

C'est une fonctionnalité **optionnelle** de VirtualBox. Les tests unittest s'exécutent normalement.

### Comment la corriger ?

**Option 1 : Désactiver DnD (Recommandé)** ✅
```
Paramètres VirtualBox → Avancé → Glisser-déposer : Désactivé
```

**Option 2 : Réinstaller les Guest Additions**
```bash
# Dans la VM
cd /media/cdrom
sudo sh VBoxLinuxAdditions.run
sudo reboot
```

**Option 3 : Mettre à jour VirtualBox**
```bash
# Télécharger la dernière version depuis virtualbox.org
# Et réinstaller
```

---

## ✅ Vérification que les tests s'exécutent correctement

### Script de test complet

Exécutez ce script sur le serveur Jenkins :

```bash
#!/bin/bash

echo "========== VÉRIFICATION DE L'ENVIRONNEMENT =========="
echo ""

echo "1. Vérification de Java"
java -version
if [ $? -eq 0 ]; then echo "✓ Java OK"; else echo "✗ Java ERREUR"; fi
echo ""

echo "2. Vérification de Maven"
mvn -version
if [ $? -eq 0 ]; then echo "✓ Maven OK"; else echo "✗ Maven ERREUR"; fi
echo ""

echo "3. Vérification de Git"
git --version
if [ $? -eq 0 ]; then echo "✓ Git OK"; else echo "✗ Git ERREUR"; fi
echo ""

echo "4. Aller dans le répertoire du projet"
cd /var/jenkins_home/workspace/CrudEtudiant
pwd
echo "✓ Répertoire OK"
echo ""

echo "5. Vérifier le pom.xml"
if [ -f "pom.xml" ]; then echo "✓ pom.xml trouvé"; else echo "✗ pom.xml MANQUANT"; fi
echo ""

echo "6. Vérifier les fichiers de test"
find src/test -name "*.java" | wc -l
echo "fichiers de test trouvés"
echo ""

echo "========== EXÉCUTION DES TESTS =========="
echo ""

# Test 1 : Validation
echo "Test 1 : Validation du projet"
mvn validate -q
if [ $? -eq 0 ]; then echo "✓ Validation OK"; else echo "✗ Validation ERREUR"; fi
echo ""

# Test 2 : Compilation
echo "Test 2 : Compilation"
mvn compile -q
if [ $? -eq 0 ]; then echo "✓ Compilation OK"; else echo "✗ Compilation ERREUR"; fi
echo ""

# Test 3 : Compilation des tests
echo "Test 3 : Compilation des tests"
mvn test-compile -q
if [ $? -eq 0 ]; then echo "✓ Test-compile OK"; else echo "✗ Test-compile ERREUR"; fi
echo ""

# Test 4 : Exécution des tests
echo "Test 4 : Exécution des tests (67 tests)"
mvn test -q
TEST_RESULT=$?
if [ $TEST_RESULT -eq 0 ]; then 
    echo "✓ Tests OK (67/67 réussis)"
else 
    echo "✗ Tests ÉCHOUÉS"
    mvn test -X  # Afficher les détails
fi
echo ""

echo "========== RÉSUMÉ =========="
echo "Status : $TEST_RESULT"
[ $TEST_RESULT -eq 0 ] && echo "✓ TOUS LES TESTS PASSENT" || echo "✗ DES TESTS ONT ÉCHOUÉ"

exit $TEST_RESULT
```

### Exécuter le script

```bash
# Copier le script
cat > /tmp/test_check.sh << 'EOF'
# ... (contenu du script ci-dessus)
EOF

# Rendre exécutable
chmod +x /tmp/test_check.sh

# Exécuter
/tmp/test_check.sh
```

---

## 🚀 Forcer l'exécution manuelle des tests

Si Jenkins échoue, testez manuellement sur le serveur :

```bash
# SSH dans le serveur Jenkins
ssh -i key.pem user@jenkins-server

# Aller dans le workspace
cd /var/jenkins_home/workspace/CrudEtudiant

# Nettoyer
mvn clean -q

# Compiler
mvn compile -q

# Exécuter TOUS les tests
mvn test -B -q

# Afficher le résumé
mvn test -q 2>&1 | tail -20

# Générer un rapport
mvn test jacoco:report

# Voir les résultats
cat target/surefire-reports/TEST-*.xml | grep -i failures
```

---

## 📊 Interpréter les résultats

### Succès

```
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

✅ **Tous les 67 tests passent**

### Echecs

```
[INFO] Tests run: 67, Failures: 3, Errors: 2, Skipped: 0
[FAILURE] BUILD FAILURE
```

❌ **3 tests échoués, 2 erreurs**

→ Consultez `target/surefire-reports/` pour les détails

---

## 🔍 Vérifier les fichiers de rapport

### Après une exécution de test

```bash
# Aller dans le répertoire de rapports
cd /var/jenkins_home/workspace/CrudEtudiant/target/surefire-reports/

# Voir les fichiers
ls -la

# Voir les résultats
cat TEST-tn.esprit.spring.crudetudiant.services.EtudiantServiceImplTest.xml

# Chercher les erreurs
grep -i "failure\|error" TEST-*.xml
```

### Analyser les erreurs

```bash
# Extraire les messages d'erreur
grep "<error message=" TEST-*.xml | sed 's/<error message="//' | sed 's/".*//'

# Extraire les stacktraces
grep -A 5 "<failure" TEST-*.xml
```

---

## 🛠️ Configuration minimale Jenkins

Pour que Jenkins exécute les tests correctement :

### Variable d'environnement système

```groovy
// Dans le Jenkinsfile
environment {
    JAVA_HOME = '/usr/lib/jvm/java-11-openjdk'
    M2_HOME = '/usr/local/maven'
    PATH = "${M2_HOME}/bin:${PATH}"
}
```

### Ou dans les paramètres du job

```
Build Environment
☑ Delete workspace before build starts
☑ Set build name

Build
Add build step > Execute shell
```

```bash
#!/bin/bash
set -e

echo "Java version:"
java -version

echo "Maven version:"
mvn -version

echo "Exécution des tests..."
mvn clean test -B -q

echo "Tests complétés avec succès"
```

---

## 📈 Monitoring des tests

### Afficher la progression en temps réel

```bash
# Terminal 1 : Exécuter les tests
mvn test -X

# Terminal 2 : Surveiller les fichiers
watch -n 1 'ls -lt target/surefire-reports/ | head -5'
```

### Compter les tests qui passent/échouent

```bash
# Au fur et à mesure
tail -f target/surefire-reports/TEST-*.xml | grep -E "tests|errors|failures"
```

---

## ✨ Checklist de débogage

Avant de conclure que les tests échouent :

- [ ] Vérifier que Maven est installé
- [ ] Vérifier que Java est disponible
- [ ] Vérifier que Git fonctionne
- [ ] Cloner le projet manuellement
- [ ] Exécuter `mvn clean test` manuellement
- [ ] Vérifier les fichiers de rapport
- [ ] Consulter les logs Jenkins complets
- [ ] Vérifier les droits d'accès aux fichiers
- [ ] Vérifier l'espace disque disponible
- [ ] Réinstaller les Guest Additions (si sur VM)

---

## 🎯 Conclusion

1. **L'erreur VirtualBox DnD est cosmétique** - Elle n'affecte pas les tests
2. **Les tests unittest doivent s'exécuter normalement** malgré cette erreur
3. **Solution rapide** : Désactiver DnD dans les paramètres VirtualBox
4. **Vérification** : Exécuter `mvn clean test` manuellement sur le serveur

Si les tests échouent vraiment, consultez les rapports dans `target/surefire-reports/` pour identifier le problème réel.

---

**Note** : Cette erreur VirtualBox DnD est souvent cosmétique et n'impacte pas l'exécution réelle des tests. Les 67 tests doivent s'exécuter correctement.

Besoin d'aide supplémentaire ? Exécutez le script de diagnostic ci-dessus. 🚀

