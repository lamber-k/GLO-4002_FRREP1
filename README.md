# GLO-4002_FRREP1

## Général ##
Projet de session de l'équipe 1 pour le cours GLO-4002 :

    http://glo3000.ift.ulaval.ca/wiki/
    https://github.com/lamber-k/GLO-4002_FRREP1

## Matricules ##

IDUL            | Nom
--------------- | -------------
111 107 301     | Lambert Kevin
111 107 307     | Rafaël Caso
910 060 845     | François Renaud-Philippon
111 107 946     | Mickaël Loubriat
909 212 507     | Mathieu Simard
111 107 311     | Maxime Charles

## Avancement ##

 Story  | Scenario                                          | Status
--------|-------------------------------------------------- | -------------
01      | Assigner périodiquement des salles aux demandes   | Finis
02      | Assignation en lot des salles aux demandes        | Finis
03      | Maximiser les places dans la salle                | Finis
04      | Ordonner les demandes par priorité                | Finis
05      | Notifier par courriel après l'assignation         | Finis
06      | Annuler une demande                               | Finis
07      | Notifier par courriel lors d'une annulation       | Finis
08      | Afficher une demande                              | Finis
09      | Permettre la réservation d'une salle              | Finis
10      | Afficher les demandes d'un organisateur           | Finis
11      | Conserver l'historique                            | En cours

## Utilisation de la bibliothèque ##

Pour lancer l'application, nous utilisons le StartupMain contenus dans le module Marv1n-web. Il va automatiquement
lancer le service REST sur le port 8080.

## Lancement des tests ##

Pour lancer les tests, nous faisons un mvn integration-test

## Notes aux correcteurs ##

La story pour persister est présente, mais non terminé, puisque nous n'avons pas fait les liens entre les deux
repository. Il en résultait des comportements indéfinis lors du lancement, rendant notre projet non fonctionnel.

## Tests non réalisés ##

En l'état, le test de flot n'est pas fonctionnel, puisque l'on ne peut pas annuler de demander par le service REST.
Nous n'avons pas fait l'implémentation par les lignes de codes.
