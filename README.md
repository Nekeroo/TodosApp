# TodosApp

Cette application à pour objectif de réaliser des tests d'intégrations et de mettre en pratique les connaissances acquises au cours de la formation.

## Installation

Avant de pouvoir lancer l'application, il vous faudra ajouter un fichier *.env.properties*

```
DATABASE_URL=url_db
DATABASE_USER=votre_user
DATABASE_PASSWORD=votre_password
JWT_SECRET_KEY=NzYzMjM2NTMxNzYxNDQ1MjA2NTgxNzI1Njk0MTUzNjU1NjU5Njk2ODcyNjMzNzU1MTYxNjE3OTUzNTM3NzU3
```

Ces informations permettent de :

* Se connecter au service de base de données PostgreSQL
* Créer des token JWT

De plus, vous pourrez lancer la commande :

```bash
mvn clean install
```

Cela installera les dépendances, lancera les tests, génèrera un rapport de tests et le copiera dans le dossier `target/`

## Fonctionnaltiés :

* Créer des utilisateurs
* Modifier des utilisateurs
* Supprimer des utilisateurs

* Créer des todos
* Modifier des todos
* Supprimer des todos
* Filtrer des todos (Par statut, utilisateur affecté, etc.)

## Lancement

Afin de lancer l'application, nous recommandons d'utiliser mvn via la commande suivante :

```bash
mvn spring-boot:run
```