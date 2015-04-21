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
import ca.ulaval.glo4002.persistence.hibernate.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.hibernate.PersonRepositoryHibernate;
import ca.ulaval.glo4002.persistence.hibernate.RoomRepositoryHibernate;
import ca.ulaval.glo4002.persistence.inmemory.PersonRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class LocatorServicesModule implements LocatorModule {

    @Override
    public void load(LocatorContainer container) {
        container.register(EntityManagerFactory.class, EntityManagerFactoryProvider.getFactory());
        PersonRepository personRepository = new PersonRepositoryInMemory();
        container.register(RequestRepository.class, new RequestRepositoryInMemory());
        container.register(PersonRepository.class, personRepository);
        container.register(RoomRepository.class, new RoomRepositoryInMemory());
        container.register(EmailValidator.class, new JavaxMailValidator());
        try {
            container.register(NotificationFactory.class, new MailNotificationFactory(new JavaxMailSender(new JavaxMailTransporter()), personRepository));
        } catch (IOException exception) {
            // TODO LOG
        }
    }
}
