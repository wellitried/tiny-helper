package email;

import dao.repositories.EmailOptionRepository;
import dao.repositories.MessageRepository;
import email.emailoption.EmailOption;
import message.Message;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MailSender {

    private final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private final Mailer mailer;
    private final MessageRepository messageRepository;
    private final String fromAddress;
    private final String fromName;

    @Inject
    MailSender(EmailOptionRepository emailOptionRepository, MessageRepository messageRepository) {

        this.messageRepository = messageRepository;

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

    public void sendMessage(Message message) {

        Email email = buildEmail(message.getRecipient(), message.getSubject(), message.getText());

        try {
            mailer.sendMail(email);
            messageRepository.markMessageAsSent(message);

            logger.info("Email was sent: " + email);
        } catch (MailException e) {
            logger.error("Error occurred while sending message: " + email, e);
        }
    }

    private Email buildEmail(String recipient, String subject, String message) {

        return EmailBuilder.startingBlank()
                .from(fromName, fromAddress)
                .to(recipient)
                .withHTMLText(message)
                .withSubject(subject)
                .buildEmail();
    }
}
