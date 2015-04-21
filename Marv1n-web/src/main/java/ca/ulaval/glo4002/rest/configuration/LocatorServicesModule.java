package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.notification.mail.MailNotificationFactory;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.locator.LocatorContainer;
import ca.ulaval.glo4002.locator.LocatorModule;
import ca.ulaval.glo4002.mail.JavaxMailSender;
import ca.ulaval.glo4002.mail.JavaxMailTransporter;
import ca.ulaval.glo4002.mail.JavaxMailValidator;
import ca.ulaval.glo4002.persistence.hibernate.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class LocatorServicesModule implements LocatorModule {

    @Override
    public void load(LocatorContainer container) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());
        container.register(EntityManagerFactory.class, entityManagerFactory);
        PersonRepository personRepository = new PersonRepositoryHibernate();
        container.register(RequestRepository.class, new RequestRepositoryHibernate());
        container.register(PersonRepository.class, personRepository);
        container.register(RoomRepository.class, new RoomRepositoryHibernate());
        container.register(EmailValidator.class, new JavaxMailValidator());
        try {
            container.register(NotificationFactory.class, new MailNotificationFactory(new JavaxMailSender(new JavaxMailTransporter()), personRepository));
        } catch (IOException exception) {
            // TODO LOG
        }
    }
}
