Assigner des salles

Narrative:
Afin maximiser l'utilisation des salles et de permettre à un maximum de personnes d'y avoir accès
En tant qu'entreprise
Je veux avoir un mécanisme d'assignation automatisé des salles

Scenario: Les demandes d'une même priorité sont traitées selon leur ordre d'arrivée
Given a pendingRequest system
And a new reservation with medium priority
And a second reservation with medium priority
And a room fiting medium priority request
And a second room fiting medium priority request
And a sorting request by priority strategy
And an evaluation strategy
When I treat pending reservation
Then same priority demads are treat in order of arrival

Scenario: La demande est assignée à la première salle disponible
Given a new reservation
And evaluation strategy assign to first available room
And a sorting request strategy
And a room
And a second room
When I treat pending reservation
Then the reservation should be assigned to the first available room

Scenario: Les demandes sont accumulées et traitées aux X minutes
Given a request treatment with a scheduler
When I start the scheduler to call the request treatment periodicaly
Then pending reservations are being treated periodicaly

Scenario: Les demandes sont traitées selon leur priorité (de 1 à 5)
Given a pendingRequest system
And multiple pending reservation with different priority
And multiple avalible room fiting request
And evaluation strategy assign to first available room
And a sorting request by priority strategy
When I treat pending reservation
Then pending reservation are being treated in order of priority

Scenario: La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given a new reservation
And a maximize seats evaluation strategy
And a sorting request strategy
And a first assigned room with lower capacity
And a second unassigned room with medium capacity
And a third unassigned room with higher capacity
When I treat pending reservation
Then the unassigned room with minimum seats, but enough, should have been assigned

Scenario: En cas d'égalité, une des salles est sélectionnée (pas de condition particulière)
Given a new reservation
And a maximize seats evaluation strategy
And a sorting request strategy
And a first unassigned room with medium capacity
And a second unassigned room with medium capacity
When I treat pending reservation
Then an unassigned room with minimum seats, but enough, should have been assigned

Scenario: Lorsque la limite de X est atteinte et que les demandes sont traitées, le compteur qui traite les demandes aux X minutes est réinitialisé
Given a new reservation
And a limit of pending request
And a sorting request strategy
And an evaluation strategy
And a request treatment with a scheduler
When the limit of pending reservation is reached
Then the pending reservation are being immediately treated
And the scheduler restart the timer
