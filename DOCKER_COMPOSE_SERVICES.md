🐳 COMPRENDRE LES SERVICES DOCKER-COMPOSE
=========================================

## 🔹 Qu'est-ce qu'un "service" dans docker-compose.yml ?

Un service = un conteneur géré par Docker Compose

Exemple de votre docker-compose.yml:

```yaml
services:
  mysql:                    ← SERVICE 1: mysql
    image: mysql:8.0
    ...

  springboot-app:           ← SERVICE 2: springboot-app
    image: yourdockerhubuser/crud-etudiant:latest
    ...
```

---

## 🎯 LE NOM DU SERVICE = HOSTNAME RÉSEAU DOCKER

### Comment fonctionne le DNS Docker ?

Quand vous déclarez:

```yaml
services:
  mysql:
    image: mysql:8.0
```

Docker crée automatiquement:

✅ 1 réseau interne (bridge network)
✅ 1 entrée DNS: `mysql` → IP du conteneur
✅ Tous les services peuvent se joindre via ce nom

---

## ❌ ERREUR CRITIQUE (que vous aviez!)

### Avant:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/CrudEtudiant
```

🔴 POURQUOI C'EST FAUX ?

1. `localhost` = le conteneur lui-même (boucle locale)
2. Le conteneur Spring Boot essaie de se connecter à lui-même
3. MySQL est dans un AUTRE conteneur
4. Résultat: Connection refused ! ❌

---

## ✅ SOLUTION: UTILISER LE NOM DU SERVICE

### Après:
```properties
spring.datasource.url=jdbc:mysql://mysql:3306/CrudEtudiant
```

🟢 POURQUOI C'EST CORRECT ?

1. `mysql` = nom du service déclaré dans docker-compose.yml
2. Docker résout automatiquement mysql → IP du conteneur MySQL
3. Spring Boot se connecte au bon conteneur
4. Résultat: Connection OK ! ✅

---

## 📚 ANALOGIE SIMPLE

Imaginez une petite ville:

```
┌─────────────────────────────────────────┐
│   RÉSEAU DOCKER (docker-compose)       │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │ Conteneur MySQL                 │   │
│  │ Nom du service: "mysql"         │   │
│  │ DNS interne: mysql:3306         │   │
│  └─────────────────────────────────┘   │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │ Conteneur Spring Boot           │   │
│  │ Adresse connection:             │   │
│  │ jdbc:mysql://mysql:3306/CrudDB  │   │
│  └─────────────────────────────────┘   │
│                                         │
└─────────────────────────────────────────┘

Le nom "mysql" est une ADRESSE dans cette ville.
```

---

## 🔍 COMMENT CONNAÎTRE LE NOM DU SERVICE ?

### Méthode 1️⃣ : Lire le fichier docker-compose.yml

Toujours après le mot `services:`:

```yaml
services:
  ↓
  mysql:                    ← NOM DU SERVICE = "mysql"
    image: mysql:8.0
  
  springboot-app:           ← NOM DU SERVICE = "springboot-app"
    image: yourdockerhubuser/crud-etudiant:latest
```

**C'est le mot que vous écrivez à gauche du deux-points.**

---

### Méthode 2️⃣ : Vérifier avec Docker après lancement

```bash
docker compose ps
```

Résultat:
```
NAME              SERVICE              IMAGE
mysql-db          mysql                mysql:8.0
springboot-app    springboot-app       yourdockerhubuser/crud-etudiant:latest
```

La colonne **SERVICE** = ce que vous utilisez dans JDBC.

---

### Méthode 3️⃣ : Tester la connexion depuis un conteneur

```bash
docker exec -it springboot-app sh -c "ping mysql"
```

✅ Si ça répond = le nom du service est correct
❌ Si "host not found" = mauvais nom ou docker-compose n'est pas lancé

---

## ⚠️ DIFFÉRENCES IMPORTANTES

### `service name` vs `container_name`

```yaml
services:
  mysql:                     ← NOM DU SERVICE (pour DNS)
    container_name: mysql-db ← NOM DU CONTENEUR (humain)
    image: mysql:8.0
```

| Propriété | Sert à quoi | Pour JDBC |
|-----------|-------------|----------|
| `service` (mysql) | DNS interne Docker | ✅ À utiliser |
| `container_name` (mysql-db) | Nom humain, affichage | ❌ Ne pas utiliser |
| `ports` (3306:3306) | Accès depuis hôte | Pour MySQL CLI |
| `localhost` | Boucle locale | ❌ JAMAIS entre conteneurs |

**En résumé:**
- **Entre conteneurs** → Utilisez le SERVICE NAME
- **Depuis votre PC** → Utilisez localhost ou container_name

---

## 🎯 VOTRE CAS EXACTEMENT

### docker-compose.yml:
```yaml
services:
  mysql:                    ← C'EST LE NOM QUE VOUS UTILISEREZ
    image: mysql:8.0
    container_name: mysql-db

  springboot-app:
    image: yourdockerhubuser/crud-etudiant:latest
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/CrudEtudiant
                                        ↑
                                   NOM DU SERVICE
```

### application.properties:
```properties
spring.datasource.url=jdbc:mysql://mysql:3306/CrudEtudiant
                                   ↑
                              C'EST LE MÊME
```

---

## 📊 TABLE DE RÉFÉRENCE

| Situation | Utilisez | Exemple |
|-----------|----------|---------|
| Spring Boot → MySQL (dans Docker) | service name | `mysql` |
| MySQL CLI depuis hôte | localhost | `mysql -h localhost -u root` |
| MySQL CLI depuis conteneur | service name | `mysql -h mysql -u root` |
| Port mapping | 3306:3306 | `docker run -p 3306:3306` |
| Healthcheck | localhost | `curl http://localhost:8089/health` |

---

## 🧪 TEST RAPIDE

Pour vérifier que tout fonctionne:

```bash
# Lancer docker-compose
docker compose up -d

# Vérifier les services
docker compose ps

# Voir les logs de Spring Boot
docker compose logs springboot-app

# Tester la connexion
curl http://localhost:8089/actuator/health

# Arrêter tout
docker compose down
```

---

## 💡 PHRASE À RETENIR

"Docker Compose crée un réseau interne où chaque service a un DNS qui porte le nom du service. 
Les conteneurs se communiquent via ces noms, jamais via localhost. 
Pour se connecter au service mysql depuis Spring Boot, j'utilise l'adresse `mysql:3306`."

---

✅ VOUS AVEZ COMPRIS ! 🎉


