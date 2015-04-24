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
Then pending reservations are being treated periodically

Scenario: Les demandes sont traitées séquenciellement
Given multiple pending reservation
And pending reservation treated sequentially
When I treat pending reservation
Then pending reservation are being treated in order

Scenario: Lorsque la limite de X est atteinte et que les demandes sont traitées, le compteur qui traite les demandes aux X minutes est réinitialisé
Given multiple pending reservation
When the limit of pending reservation is reached
Then the pending reservation are being immediately treated
And the scheduler restart the timer

Scenario: Les demandes sont traitées selon leur priorité (de 1 à 5)
Given multiple pending reservation with different priority
And pending reservation treated by priority
When I treat pending reservation
Then pending reservation are being treated in order of priority

Scenario: Les demandes d'une même priorité sont traitées selon leur ordre d'arrivée
Given multiple pending reservation with same priority
And pending reservation treated by priority
When I treat pending reservation
Then pending reservation are being treated in order

Scenario: La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given pending reservation with different capacity needed
And a maximize strategy
When I treat pending reservation
Then reservations should have been assigned in order to maximize capacity

Scenario: En cas d'égalité, une des salles est sélectionnée (pas de condition particulière)
Given a new pending reservation
And a maximize strategy
And multiple rooms with same capacity
When I treat pending reservation
Then reservation should have been assigned to one of the room
