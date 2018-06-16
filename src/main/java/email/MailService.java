package email;

import email.emailoption.EmailOption;
import email.emailoption.EmailOptionRepository;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MailService {

    private final Mailer mailer;

    private final String fromAddress;

    private final String fromName;


    @Inject
    MailService(EmailOptionRepository emailOptionRepository) {

        EmailOption defaultOption = emailOptionRepository.getDefaultOption();

        mailer = MailerBuilder
                .withSMTPServer(
                        defaultOption.getHost(),
                        defaultOption.getPort(),
                        defaultOption.getUsername(),
                        defaultOption.getPassword())
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        fromAddress = defaultOption.getFromAddress();
        fromName = defaultOption.getFromName();
    }

    public void sendMail(String recipient, String subject, String message) {

        final Email email = EmailBuilder.startingBlank()
                .from(fromName, fromAddress)
                .to(recipient)
                .withHTMLText(message)
                .withSubject(subject)
                .buildEmail();

        try {
            mailer.sendMail(email);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
