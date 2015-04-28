Modification des demandes

Narrative:
Afin de support les changements d'horaires et maximiser la productivité
En tant qu'organisateur
Je veux pouvoir modifier ou annuler mes réservations

Scenario: Il est possible d'annuler une réservation déjà assignée
Given an existing assigned reservation
When I cancel this reservation
Then the assigned reservation should have been cancelled
And the status of the assigned reservation should have changed

Scenario: Il est possible d'annuler une réservation en attente de traitement
Given an existing pending reservation
When I cancel this reservation
Then the pending reservation should have been cancelled

Scenario: La demande est archivée
Given an existing assigned reservation
When I cancel this reservation
Then the reservation should have been archived

Given an existing pending reservation
When I cancel this reservation
Then the reservation should have been archived

Scenario: La salle assignée est retirée
Given an existing assigned reservation
When I cancel this reservation
Then the room should have been unassigned

Scenario: Le statut de la réservation est modifié pour un statut d'annulation
Given an existing assigned reservation
When I cancel this reservation
Then the status of the assigned reservation should have changed