Modification des demandes

Narrative:
Afin de support les changements d'horaires et maximiser la productivité
En tant qu'organisateur
Je veux pouvoir modifier ou annuler mes réservations

Scenario:  Il est possible d'annuler une réservation déjà assignée
Given An existing reserved reservation
When I cancel a reserved reservation
Then The reserved reservation is cancel

Scenario: Il est possible d'annuler une réservation en attente de traitement
Given An existing pending reservation
When I cancel a pending reservation
Then The pending reservation is cancel

Scenario:  La demande est archivée
Given An existing reservation
When I accepte a reservation
Then The reservation is archive

Given An existing reservation
When I cancel a reservation
Then The reservation is archive