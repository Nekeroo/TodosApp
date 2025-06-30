## Party 1

#### Story 1

**En tant qu'** utilisateur,  
**Je veux** créer une nouvelle tâche avec un titre et une description optionnelle,  
**Afin de** pouvoir organiser mes activités à réaliser.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** je fournis un titre valide (non vide, maximum 100 caractères), **LORSQUE** je crée une tâche, **ALORS** elle est créée avec un ID unique, le titre fourni, une description vide par défaut, une date de création et le statut "TODO"
- **ÉTANT DONNÉ QUE** je fournis un titre et une description valide (maximum 500 caractères), **LORSQUE** je crée une tâche, **ALORS** elle est créée avec le titre et la description fournis
- **ÉTANT DONNÉ QUE** je fournis un titre vide ou composé uniquement d'espaces, **LORSQUE** je tente de créer une tâche, **ALORS** j'obtiens une erreur "Title is required"
- **ÉTANT DONNÉ QUE** je fournis un titre de plus de 100 caractères, **LORSQUE** je tente de créer une tâche, **ALORS** j'obtiens une erreur "Title cannot exceed 100 characters"
- **ÉTANT DONNÉ QUE** je fournis une description de plus de 500 caractères, **LORSQUE** je tente de créer une tâche, **ALORS** j'obtiens une erreur "Description cannot exceed 500 characters"
- **ÉTANT DONNÉ QUE** je fournis une titre qui commence et/ou termine par des espace, **LORSQUE** je crée une tâche, **ALORS** elle est créee avec le titre fourni, sans espaces au début ni à la fin
- **ÉTANT DONNÉ QUE** j'ai une tâche nouvellement créée, **LORSQUE** je la consulte, **ALORS** sa date de création correspond au moment de création à la seconde près

#### Story 2

**En tant qu'** utilisateur,  
**Je veux** consulter les détails complets d'une tâche existante,  
**Afin de** voir toutes les informations associées.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai une tâche existante avec ID valide, **LORSQUE** je consulte cette tâche, **ALORS** j'obtiens tous ses détails : ID, titre, description, statut, date de création, etc..
- **ÉTANT DONNÉ QUE** je consulte une tâche avec un ID inexistant, **LORSQUE** je fais la demande, **ALORS** j'obtiens une erreur "Task not found" avec, si web, le code 404
- **ÉTANT DONNÉ QUE** je consulte une tâche avec un ID au mauvais format, **LORSQUE** je fais la demande, **ALORS** j'obtiens une erreur "Invalid ID format"

#### Story 3

**En tant qu'** utilisateur,  
**Je veux** modifier le titre et/ou la description d'une tâche existante,  
**Afin de** corriger ou préciser les informations.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je modifie son titre avec une valeur valide, **ALORS** le nouveau titre est sauvegardé et les autres champs restent inchangés
- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je modifie sa description avec une valeur valide, **ALORS** la nouvelle description est sauvegardée et les autres champs restent inchangés
- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je modifie à la fois le titre et la description, **ALORS** les deux modifications sont sauvegardées
- **ÉTANT DONNÉ QUE** je tente de modifier le titre d'une tâche avec une valeur vide, **LORSQUE** je soumets la modification, **ALORS** j'obtiens une erreur "Title is required"
- **ÉTANT DONNÉ QUE** je tente de modifier une tâche avec un titre de plus de 100 caractères, **LORSQUE** je soumets, **ALORS** j'obtiens une erreur "Title cannot exceed 100 characters"
- **ÉTANT DONNÉ QUE** je tente de modifier une tâche avec une description de plus de 500 caractères, **LORSQUE** je soumets, **ALORS** j'obtiens une erreur "Description cannot exceed 500 characters"
- **ÉTANT DONNÉ QUE** je tente de modifier une tâche inexistante, **LORSQUE** j'utilise un ID invalide, **ALORS** j'obtiens une erreur "Task not found"
- **ÉTANT DONNÉ QUE** je tente de modifier des champs non modifiables (ID, date de création, statut), **LORSQUE** je soumets ces modifications, **ALORS** ces champs sont ignorés et seuls titre/description sont pris en compte

> Attention, à bien factoriser votre code, certaines vérifications ont déjà été faites lors de la création de la tâche. Évitez de les redonder, mais assurez-vous de tester l'ensemble des cas.

## Story 4

**En tant qu'** utilisateur,  
**Je veux** faire évoluer le statut d'une tâche (TODO → ONGOING → DONE),  
**Afin de** suivre l'avancement de mes activités.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je change son statut vers "TODO", "ONGOING" ou "DONE", **ALORS** le statut est mis à jour avec succès
- **ÉTANT DONNÉ QUE** je tente de changer le statut d'une tâche vers une valeur invalide, **LORSQUE** je soumets le changement, **ALORS** j'obtiens une erreur "Invalid status. Allowed values: TODO, ONGOING, DONE"
- **ÉTANT DONNÉ QUE** je tente de changer le statut d'une tâche inexistante, **LORSQUE** j'utilise un ID invalide, **ALORS** j'obtiens une erreur "Task not found"

## Story 5

**En tant qu'** utilisateur,  
**Je veux** supprimer définitivement une tâche,  
**Afin de** nettoyer ma liste des tâches devenues inutiles.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je la supprime, **ALORS** elle n'apparaît plus dans la liste des tâches
- **ÉTANT DONNÉ QUE** j'ai supprimé une tâche, **LORSQUE** je tente de la consulter, de la supprimer, de la modifier ou de change son status par son ID, **ALORS** j'obtiens une erreur "Task not found"

## Story 6

**En tant qu'** utilisateur,  
**Je veux** consulter la liste de mes tâches par pages,  
**Afin de** naviguer efficacement même avec de nombreuses tâches.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je demande la première page avec une taille de 10, **ALORS** j'obtiens au maximum 10 tâches et les informations de pagination (page courante, total de pages, total d'éléments)
- **ÉTANT DONNÉ QUE** j'ai plus de 10 tâches, **LORSQUE** je demande la deuxième page, **ALORS** j'obtiens les tâches suivantes avec les bonnes informations de pagination
- **ÉTANT DONNÉ QUE** je demande une page au-delà du nombre total de pages, **LORSQUE** j'exécute la requête, **ALORS** j'obtiens une liste vide avec les informations de pagination correctes
- **ÉTANT DONNÉ QUE** je ne spécifie pas de paramètres de pagination, **LORSQUE** je demande la liste, **ALORS** j'obtiens la première page avec une taille par défaut de 20 éléments
- **ÉTANT DONNÉ QUE** je spécifie une taille de page invalide (négative ou zéro), **LORSQUE** je fais la demande, **ALORS** j'obtiens une erreur "Invalid page size"
- **ÉTANT DONNÉ QUE** j'ai aucune tâche, **LORSQUE** je demande la liste, **ALORS** j'obtiens une liste vide avec les informations de pagination (0 éléments, 0 pages)
  User Stories : Phase 2 - Organisation et recherche
  US007 - Rechercher des tâches
  **En tant qu'** utilisateur,  
  **Je veux** rechercher mes tâches par mots-clés dans le titre ou la description,  
  **Afin de** retrouver rapidement une tâche spécifique.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai des tâches contenant un mot-clé dans le titre, **LORSQUE** je recherche ce terme, **ALORS** seules les tâches correspondantes sont retournées
- **ÉTANT DONNÉ QUE** j'ai des tâches contenant un mot-clé dans la description, **LORSQUE** je recherche ce terme, **ALORS** seules les tâches correspondantes sont retournées
- **ÉTANT DONNÉ QUE** j'ai des tâches contenant un mot-clé dans le titre ET la description, **LORSQUE** je recherche ce terme, **ALORS** toutes ces tâches sont retournées (sans doublon)
- **ÉTANT DONNÉ QUE** je recherche un terme inexistant, **LORSQUE** j'exécute la recherche, **ALORS** j'obtiens une liste vide
- **ÉTANT DONNÉ QUE** je recherche avec une chaîne vide, **LORSQUE** j'exécute la recherche, **ALORS** toutes les tâches sont retournées
- **ÉTANT DONNÉ QUE** je recherche avec des majuscules/minuscules, **LORSQUE** j'exécute la recherche, **ALORS** la recherche est insensible à la casse
- **ÉTANT DONNÉ QUE** j'ai de nombreux résultats de recherche, **LORSQUE** je fais la recherche, **ALORS** les résultats sont paginés comme la liste normale
  User Stories : Phase 3 - Gestion des utilisateurs

