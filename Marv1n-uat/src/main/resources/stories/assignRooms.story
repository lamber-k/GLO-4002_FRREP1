Assigner des salles

Narrative:
Afin maximiser l'utilisation des salles et de permettre à un maximum de personnes d'y avoir accès
En tant qu'entreprise
Je veux avoir un mécanisme d'assignation automatisé des salles

Scenario: La demande est assignée à la première salle disponible
Given a new pending reservation
And pending reservation assigned to the first available room
When I treat pending reservation
Then the reservation should be assigned to the first available room

Scenario: Les demandes sont accumulées et traitées aux X minutes
Given a request treatment with a scheduler
When I start the scheduler to call the request treatment every 1 minutes
Then pending reservations are treat periodically

Scenario: Les demandes sont traitées séquenciellement
Given multiple pending reservation
And pending reservation treated sequentially
When I treat pending reservation
Then pending reservation are treat in order

Scenario: La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given pending reservation with different capacity needed
And a maximize strategy
When I treat pending reservation
Then reservations should have been assigned in order to maximize capacity
