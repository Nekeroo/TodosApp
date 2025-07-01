## Phase 2


#### Story 7

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


#### Story 8

**En tant qu'** utilisateur,  
**Je veux** filtrer mes tâches par statut (TODO, ONGOING, DONE),  
**Afin de** me concentrer sur un type d'activité.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai des tâches avec différents statuts, **LORSQUE** je filtre par "TODO", "ONGOING" ou "DONE", **ALORS** seules les tâches avec le statut correspondant sont retournées
- **ÉTANT DONNÉ QUE** je filtre par un statut et qu'aucune tâche ne correspond, **LORSQUE** j'applique le filtre, **ALORS** j'obtiens une liste vide
- **ÉTANT DONNÉ QUE** je filtre par un statut invalide, **LORSQUE** j'applique le filtre, **ALORS** j'obtiens une erreur "Invalid filter status"
- **ÉTANT DONNÉ QUE** je filtre par statut et que j'ai de nombreux résultats, **LORSQUE** j'applique le filtre, **ALORS** les résultats sont paginés


#### Story 9

**En tant qu'** utilisateur,  
**Je veux** trier mes tâches par date de création, titre ou statut,  
**Afin de** organiser l'affichage selon mes besoins.

**Critères d'acceptation :**
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je trie par date de création ascendante, **ALORS** les tâches sont affichées de la plus ancienne à la plus récente
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je trie par date de création descendante, **ALORS** les tâches sont affichées de la plus récente à la plus ancienne
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je trie par titre ascendant, **ALORS** les tâches sont affichées par ordre alphabétique
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je trie par titre descendant, **ALORS** les tâches sont affichées par ordre alphabétique inversé
- **ÉTANT DONNÉ QUE** j'ai plusieurs tâches, **LORSQUE** je trie par statut, **ALORS** les tâches sont groupées par statut dans l'ordre : TODO, ONGOING, DONE
- **ÉTANT DONNÉ QUE** je ne spécifie pas de tri, **LORSQUE** je consulte la liste, **ALORS** les tâches sont triées par date de création descendante par défaut
- **ÉTANT DONNÉ QUE** je spécifie un critère de tri invalide, **LORSQUE** je fais la demande, **ALORS** j'obtiens une erreur "Invalid sort criteria"
- **ÉTANT DONNÉ QUE** je combine tri et filtre, **LORSQUE** j'applique les deux, **ALORS** le tri s'applique sur les résultats filtrés