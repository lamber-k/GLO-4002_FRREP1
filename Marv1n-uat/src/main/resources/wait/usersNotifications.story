Notifier les utilisateurs

Narrative:
Afin de prendre des actions et me rendre aux réunions
En tant qu'utilisateur
Je veux être informé lors des assignations et changements

Scenario: Pour une assignation de reservation, le courriel mentionne la salle choisie
Given an existing pending reservation
When I treat this reservation
Then an email notification should be created
And this email should mention the room

Scenario: En cas d'échec de la reservation, le courriel indique la raison du refus
Given an existing pending reservation
When the reservation fails
Then an email notification should be created
And this email should mention the failure reason

Scenario: Apres le traitement de la reservation, le courriel est envoyé à l'organisateur ayant soumis la demande et une copie au responsable des réservations de l'entreprise
Given an existing pending reservation
When I treat this reservation
Then an email notification should be created
And it should be sent to the organiser
And a copy should be sent to the company reservation head

Scenario: Apres l'annulation d'une reservation, le courriel est envoyé à tous les participants et une copie au responsable des réservations ainsi qu'à l'organisateur
Given an existing assigned reservation
When I cancel this reservation
Then an email notification should be created
And it should be sent to all participants
And a copy should be sent to the reservation head
And a copy should be sent to the organiser

Scenario: Apres l'annulation d'une reservation, le courriel indique le titre de la réservation donnée initialement par l'organisateur
Given an existing assigned reservation
When I cancel this reservation
Then an email notification should be created
And it should have the title chosen by the organiser