Assigner des salles

Narrative:
Afin maximiser l'utilisation des salles et de permettre à un maximum de personnes d'y avoir accès
En tant qu'entreprise
Je veux avoir un mécanisme d'assignation automatisé des salles

Scenario: La demande est assignée à la première salle disponible
Given a new pending reservation
When I treat pending reservation to the first available room
Then the reservation should be assigned to the first available room
