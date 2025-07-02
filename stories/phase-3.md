## Phase 3

### Story 10

**En tant qu'** administrateur,  
**Je veux** créer un nouvel utilisateur avec nom et email,  
**Afin de** préparer l'assignation de tâches.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** je fournis un nom valide (non vide, maximum 50 caractères) et un email valide, **LORSQUE** je crée un utilisateur, **ALORS** il est créé avec un ID unique, le nom et l'email fournis ainsi que sa date de création
- **ÉTANT DONNÉ QUE** je fournis un email déjà utilisé par un autre utilisateur, **LORSQUE** je tente de créer l'utilisateur, **ALORS** j'obtiens une erreur "Email already in use"
- **ÉTANT DONNÉ QUE** je fournis un email au format invalide, **LORSQUE** je tente de créer l'utilisateur, **ALORS** j'obtiens une erreur "Invalid email format"
- **ÉTANT DONNÉ QUE** je fournis un nom vide ou composé uniquement d'espaces, **LORSQUE** je tente de créer l'utilisateur, **ALORS** j'obtiens une erreur "Name is required"
- **ÉTANT DONNÉ QUE** je fournis un nom de plus de 50 caractères, **LORSQUE** je tente de créer l'utilisateur, **ALORS** j'obtiens une erreur "Name cannot exceed 50 characters"

### Story 11 

**En tant qu'** utilisateur,  
**Je veux** consulter la liste des utilisateurs disponibles,  
**Afin de** savoir à qui je peux assigner des tâches.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai plusieurs utilisateurs créés, **LORSQUE** je demande la liste des utilisateurs, **ALORS** j'obtiens tous les utilisateurs avec leurs informations (ID, nom, email)
- **ÉTANT DONNÉ QUE** j'ai de nombreux utilisateurs, **LORSQUE** je demande la liste avec pagination, **ALORS** les utilisateurs sont paginés comme les tâches
- **ÉTANT DONNÉ QUE** je n'ai aucun utilisateur, **LORSQUE** je demande la liste, **ALORS** j'obtiens une liste vide
- **ÉTANT DONNÉ QUE** je demande la liste des utilisateurs, **LORSQUE** j'exécute la requête, **ALORS** les utilisateurs sont triés par nom par défaut

### Story 12

**En tant qu'** utilisateur,  
**Je veux** assigner une tâche à un utilisateur spécifique,  
**Afin de** déléguer des responsabilités.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai une tâche existante et un utilisateur existant, **LORSQUE** j'assigne la tâche à l'utilisateur, **ALORS** l'assignation est enregistrée et visible dans les détails de la tâche
- **ÉTANT DONNÉ QUE** j'ai une tâche déjà assignée, **LORSQUE** je l'assigne à un autre utilisateur, **ALORS** l'ancienne assignation est remplacée par la nouvelle
- **ÉTANT DONNÉ QUE** j'ai une tâche assignée, **LORSQUE** je la désassigne (assigner à null/vide), **ALORS** la tâche n'est plus assignée à personne
- **ÉTANT DONNÉ QUE** je tente d'assigner une tâche à un utilisateur inexistant, **LORSQUE** j'utilise un ID utilisateur invalide, **ALORS** j'obtiens une erreur "User not found"
- **ÉTANT DONNÉ QUE** je tente d'assigner une tâche inexistante, **LORSQUE** j'utilise un ID de tâche invalide, **ALORS** j'obtiens une erreur "Task not found"

### Story 13

**En tant qu'** utilisateur,  
**Je veux** voir uniquement les tâches assignées à un utilisateur donné,  
**Afin de** suivre la charge de travail de chacun.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai des tâches assignées à différents utilisateurs, **LORSQUE** je filtre par un utilisateur spécifique, **ALORS** seules les tâches assignées à cet utilisateur sont retournées
- **ÉTANT DONNÉ QUE** j'ai des tâches non assignées, **LORSQUE** je filtre par "tâches non assignées", **ALORS** seules les tâches sans assignation sont retournées
- **ÉTANT DONNÉ QUE** je filtre par un utilisateur qui n'a aucune tâche assignée, **LORSQUE** j'applique le filtre, **ALORS** j'obtiens une liste vide
- **ÉTANT DONNÉ QUE** je filtre par un utilisateur inexistant, **LORSQUE** j'applique le filtre, **ALORS** j'obtiens une erreur "User not found"
- **ÉTANT DONNÉ QUE** je combine le filtre utilisateur avec d'autres filtres (statut, recherche), **LORSQUE** j'applique les filtres, **ALORS** tous les critères sont respectés
