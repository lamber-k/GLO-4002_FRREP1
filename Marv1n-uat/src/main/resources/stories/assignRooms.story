Assigner des salles

Narrative:
Afin maximiser l'utilisation des salles et de permettre à un maximum de personnes d'y avoir accès
En tant qu'entreprise
Je veux avoir un mécanisme d'assignation automatisé des salles

Scenario: La demande est assignée à la première salle disponible
Given a pending request
When I treat pending requests to the first available room
Then the request should be assigned to the first available room

Scenario: Les demandes sont accumulées et traitées aux X minutes


Scenario: Les demandes sont traitées séquenciellement


Scenario: La fréquence est configurable



Scenario: Assignation en lot des salles aux demandes

Scenario: Ordonner les demandes par priorité

Scenario: Maximiser les places dans la salle