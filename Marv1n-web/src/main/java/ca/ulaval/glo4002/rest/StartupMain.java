package ca.ulaval.glo4002.rest;

public class StartupMain {
    private static final int DEFAULT_PORT = 8080;

    private StartupMain() {
    }

    public static void main(String[] args) throws Exception {
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
