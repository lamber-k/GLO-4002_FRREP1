package infrastructure.rest;

import core.notification.mail.EmailValidator;
import core.notification.mail.InvalidMailAddressException;
import core.notification.mail.MailAddress;
import core.person.Person;
import core.person.PersonRepository;
import infrastructure.mail.JavaxMailValidator;
import infrastructure.persistence.PersonRepositoryHibernate;

public class StartupMain {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String []args) throws Exception {
        /*PersonRepository personRepository = new PersonRepositoryHibernate();
        try {
            EmailValidator validator = new JavaxMailValidator();
            Person p = new Person(new MailAddress("test@test.com", validator));
            personRepository.persist(p);
            Person personFound = personRepository.findByUUID(p.getID());
            if (!personFound.equals(p)) {
                throw new RuntimeException("Poil de cul");
            }
        } catch (InvalidMailAddressException e) {
            e.printStackTrace();
        }*/

        new StartupRest(DEFAULT_PORT).start();
    }
}
