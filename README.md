# Application de Routage de Paiement - README.md

Ce document décrit comment configurer, lancer et tester l'application. 
Cette application est conçue pour traiter les messages provenant d'IBM MQ Series, les stocker dans une base de données PostgreSQL, et fournir une API REST ainsi qu'une interface utilisateur Angular pour la gestion des messages et des partenaires.

---

## Prérequis

1. **Java Development Kit (JDK)** : Version 17.
2. **Apache Maven** : Pour compiler l'application backend.
3. **Node.js & npm** : Pour l'application frontend (Angular 17).
4. **Docker** : Pour exécuter IBM MQ et PostgreSQL dans des conteneurs.
5. **PostgreSQL** : Si vous n'utilisez pas Docker pour la base de données.

---

## Présentation de l'Application

- **Backend** : Application Spring Boot qui :
  - Lit les messages d'IBM MQ Series.
  - Stocke les messages dans PostgreSQL.
  - Fournit des APIs REST pour gérer les messages et les partenaires.
- **Frontend** : Application Angular qui :
  - Affiche les messages sous forme de tableau avec des détails.
  - Permet d'ajouter, mettre à jour et supprimer des partenaires.
- **Base de Données** : PostgreSQL est utilisée pour stocker les informations des messages et des partenaires.

---

## Configuration

### 1. **Configuration d'IBM MQ**

Pour exécuter IBM MQ avec Docker :

```bash
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest
```

#### Paramètres IBM MQ :

- **Nom du Gestionnaire de File (Queue Manager)** : `QM1`
- **Nom de la File** : `DEV.QUEUE.1`
- **Port** : `1414`
- **Utilisateur** : `admin`
- **Mot de Passe** : `passw0rd`

Les paramètres de connexion peuvent être ajustés dans le fichier `application.properties` du backend.

---

### 2. **Configuration de PostgreSQL**

Si vous utilisez Docker, démarrez PostgreSQL avec la commande suivante :

```bash
docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=payments -p 5432:5432 -d postgres
```

#### Paramètres de la base de données :

- **URL** : `jdbc:postgresql://localhost:5432/payments`
- **Utilisateur** : `postgres`
- **Mot de Passe** : `postgres`

Ces paramètres sont définis dans le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/payments
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

---

## Lancement de l'Application

### 1. **Backend**

#### Compilation et exécution :

```bash
cd payment-routing-backend
mvn clean install
java -jar target/payment-routing-backend.jar
```

L'application sera accessible sur [http://localhost:80](http://localhost:80).

#### Points de terminaison API REST principaux :

- **Messages** :
  - GET `/api/messages` : Liste paginée des messages.
  - GET `/api/messages/{id}` : Détails d'un message.
  - GET `/api/messages/rec` : Recevoir un message depuis IBM MQ.
- **Partenaires** :
  - GET `/api/v1/partners` : Liste paginée des partenaires.
  - POST `/api/v1/partners` : Ajouter un nouveau partenaire.
  - PUT `/api/v1/partners/{id}` : Mettre à jour un partenaire.
  - DELETE `/api/v1/partners/{id}` : Supprimer un partenaire.

---

### 2. **Frontend**

#### Installation des dépendances :

```bash
cd payment-routing-frontend
npm install
```

#### Lancer le serveur Angular :

```bash
ng serve
```

L'interface utilisateur sera accessible sur [http://localhost:4200](http://localhost:4200).

---

## Test de l'Application

### 1. **Test des APIs REST**

Utilisez **Postman** ou **cURL** pour tester les endpoints REST. Par exemple, pour ajouter un partenaire :

```bash
curl -X POST http://localhost:80/api/v1/partners 
     -H "Content-Type: application/json" 
     -d '{
           "alias": "PartenaireX",
           "type": "INBOUND",
           "direction": "INBOUND",
           "application": "AppExample",
           "processedFlowType": "MESSAGE",
           "description": "Partenaire X description"
         }'
```

### 2. **Test de l'IHM**

1. Accédez à [http://localhost:4200](http://localhost:4200).
2. Naviguez dans le menu pour :
   - Voir la liste des messages.
   - Ajouter, modifier ou supprimer des partenaires.
   - Cliquer sur un message pour afficher ses détails.

---

## Développement Futur

- **Optimisation des performances** : En cas de volumétrie élevée, envisagez des optimisations de traitement asynchrone.

---
