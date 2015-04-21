Assigner des salles

Narrative:
Afin maximiser l'utilisation des salles et de permettre à un maximum de personnes d'y avoir accès
En tant qu'entreprise
Je veux avoir un mécanisme d'assignation automatisé des salles

Scenario: Assigner périodiquement des salles aux demandes

Scenario: Assignation en lot des salles aux demandes

Scenario: Ordonner les demandes par priorité

Scenario: Maximiser les places dans la salle
Scenario: La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given an new reservation
And a maximize strategie
And a first assigned room with lower capacity
And a second unassigned room with medium capacity
And a third unassigned room with higher capacity
When I treat with the maximize seats evaluation strategy
Then the unassigned room with minimum seats, but enough, should have been assigned