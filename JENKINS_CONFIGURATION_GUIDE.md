# 🔧 Guide de configuration Jenkins pour CrudEtudiant

## 🚨 Problème identifié

**Erreur VirtualBox DnD** :
```
Le glisser-déposer de l'invité vers l'hôte a échoué.
DnD: Error: Dragging from guest to host not supported...
VBOX_E_DND_ERROR (0x80bb0011)
```

Cette erreur est causée par :
- Les Guest Additions de VirtualBox mal installées
- Ou une incompatibilité avec Jenkins s'exécutant dans une VM

---

## ✅ Solutions

### Solution 1 : Désactiver Drag & Drop dans VirtualBox (Recommandé)

Cette erreur n'affecte PAS les tests unitaires. C'est une fonctionnalité optionnelle.

#### Étapes :
1. **Arrêter la VM** (si elle est en cours d'exécution)
2. **Paramètres VirtualBox** :
   - Clic droit sur la VM → Paramètres
   - Onglet **Avancé**
   - Glisser-déposer : **Désactivé**
3. **Redémarrer la VM**

### Solution 2 : Réinstaller les Guest Additions

Si vous avez besoin du Drag & Drop :

1. **Dans la VM** :
   - Insérez l'image CD des Guest Additions
   - Exécutez le programme d'installation
   - Redémarrez la VM

2. **Ou depuis VirtualBox** :
   - Menu → Périphériques → Insérer l'image CD des additions...

### Solution 3 : Utiliser NAT au lieu de Host-only Network

Si le problème persiste :

1. **Paramètres VirtualBox** → **Réseau**
2. Changez en **NAT** ou **Réseau de pont**
3. Redémarrez la VM

---

## ✅ Configuration Jenkins optimale

### Prérequis sur le serveur Jenkins

```bash
# Vérifier que Maven est installé
mvn -version

# Vérifier que Java est disponible
java -version

# Vérifier que Git est disponible
git --version
```

### Configuration du job Jenkins

#### 1. Créer un nouveau job
- Type : **Pipeline** ou **Free-style job**

#### 2. Si c'est un job Free-style

**Build Triggers** :
- ☑ Interroger l'outil de gestion des versions SCM
- Schedule : `H/15 * * * *` (toutes les 15 minutes)

**Build Steps** :
```bash
# Étape 1 : Nettoyage
mvn clean

# Étape 2 : Compilation
mvn compile

# Étape 3 : Tests (67 tests)
mvn test

# Étape 4 : Rapport de couverture
mvn jacoco:report

# Étape 5 : Sonar (si configuré)
mvn sonar:sonar
```

**Post-build Actions** :
- ☑ Publier les résultats des tests JUnit
  - Chemin des rapports : `target/surefire-reports/*.xml`
- ☑ Publier le rapport HTML
  - Répertoire à publier : `target/site/jacoco`

#### 3. Si c'est un Pipeline (Jenkinsfile)

Votre Jenkinsfile a été mis à jour automatiquement avec :
- ✅ Étape TEST (67 tests)
- ✅ Étape RAPPORT DE COUVERTURE
- ✅ Publication des résultats
- ✅ Gestion des erreurs

---

## 🔍 Vérification de la pipeline

### Avant de lancer le build

✅ Vérifier les permissions :
```bash
# Jenkins doit avoir accès au répertoire
ls -la /var/jenkins_home/workspace/CrudEtudiant/

# Vérifier les droits Git
cd /var/jenkins_home/workspace/CrudEtudiant
git status
```

✅ Vérifier la configuration :
```bash
# Fichier pom.xml valide ?
mvn validate

# Tests compilent ?
mvn test-compile

# Tests exécutent ?
mvn test -q
```

### Exécuter le build

1. **Dans Jenkins** :
   - Allez dans votre job
   - Cliquez sur **Build Now**

2. **Surveiller la progression** :
   - Console de sortie en temps réel
   - Cliquez sur l'une des lignes pour développer les détails

3. **Analyser les résultats** :
   - Tests : Cliquez sur **Test Results**
   - Couverture : Cliquez sur **Rapport de Couverture JaCoCo**

---

## ✨ Résultat attendu dans Jenkins

### Logs de console

```
[INFO] Scanning for projects...
[INFO] Building crudEtudiant 0.0.1-SNAPSHOT
[INFO] 
[INFO] --- maven-clean-plugin:3.1.0:clean ---
[INFO] Deleting /var/jenkins_home/workspace/CrudEtudiant/target
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile ---
[INFO] Compiling 7 source files
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile ---
[INFO] Compiling 6 test source files
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test ---
[INFO] Tests run: 67, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.8:report ---
[INFO] Generating JaCoCo report...
[INFO] 
[INFO] BUILD SUCCESS
```

### Dashboard Jenkins

- ✅ **Tests Passed** : 67/67
- ✅ **Code Coverage** : >95%
- ✅ **Build Status** : SUCCESS

---

## 🐛 Troubleshooting

### Erreur : "mvn command not found"

```bash
# Ajouter Maven au PATH Jenkins
# Dans les paramètres du job, ajouter :
export PATH=$PATH:/usr/local/maven/bin

# Ou vérifier l'installation :
which mvn
```

### Erreur : "Tests fail in Jenkins but pass locally"

**Causes possibles** :
- Variables d'environnement différentes
- Permissions de fichier
- Base de données non accessible

**Solutions** :
```bash
# Exécuter avec plus de détails
mvn test -X

# Exécuter sans parallélisation
mvn test -T 1

# Afficher les résultats des tests
mvn test -e
```

### Erreur : "Permission denied"

```bash
# Vérifier les droits
ls -la target/

# Donner les droits à Jenkins
chmod -R 755 target/
```

### VirtualBox DnD Error

✅ Cette erreur n'affecte PAS les tests
✅ Solution : Désactiver DnD dans les paramètres VM

---

## 📊 Métriques de succès

Pour que votre pipeline soit complète :

| Métrique | Valeur |
|----------|--------|
| **Tests réussis** | 67/67 ✅ |
| **Temps d'exécution** | < 5 minutes |
| **Couverture de code** | > 95% |
| **Erreurs de compilation** | 0 |
| **Avertissements** | 0 (idéal) |

---

## 🚀 Prochaines étapes

1. ✅ Vérifier que Maven est installé sur le serveur Jenkins
2. ✅ Vérifier que Git est configuré
3. ✅ Créer le job Jenkins
4. ✅ Configurer le Jenkinsfile (déjà fait ✓)
5. ✅ Lancer le build
6. ✅ Analyser les résultats

---

## 📝 Commandes utiles

```bash
# Vérifier la configuration Maven
mvn help:describe

# Exécuter les tests avec rapport
mvn clean test jacoco:report -B

# Mode batch (sans interaction)
mvn test -B -q

# Avec variables d'environnement
export MAVEN_OPTS="-Xmx512m"
mvn test

# Paralléliser les tests
mvn test -T 1C -q
```

---

## 📞 Support

Si vous rencontrez toujours des problèmes :

1. **Vérifiez les logs Jenkins** : Console → Build
2. **Exécutez manuellement** : SSH dans la VM et lancez `mvn test`
3. **Consultez les rapports** : Vérifiez `target/surefire-reports/`
4. **Réinstallez les Guest Additions** : Aide VirtualBox

---

**Note importante** : L'erreur VirtualBox DnD est **cosmétique** et n'affecte pas l'exécution des tests. Les tests unitaires (67) doivent s'exécuter correctement malgré cette erreur.

Bon testing ! 🚀

