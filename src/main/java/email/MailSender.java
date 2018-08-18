package email;

import dao.repositories.EmailOptionRepository;
import dao.repositories.MessageRepository;
import email.emailoption.EmailOption;
import message.Message;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
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
    private final MessageRepository messageRepository;
    private final EmailOptionRepository emailOptionRepository;
    private final EmailBuilder emailBuilder;

    @Inject
    MailSender(EmailOptionRepository emailOptionRepository, MessageRepository messageRepository, EmailBuilder emailBuilder) {
        this.messageRepository = messageRepository;
        this.emailOptionRepository = emailOptionRepository;
        this.emailBuilder = emailBuilder;
    }

    public void sendMessage(Message message) {

        Email email = emailBuilder.buildEmail(message.getRecipient(), message.getSubject(), message.getText());

        try {
            getMailer().sendMail(email);
            messageRepository.markMessageAsSent(message);

            logger.info("Email was sent: " + email);
        } catch (MailException e) {
            logger.error("Error occurred while sending message: " + email, e);
        }
    }

    private Mailer getMailer() {

        EmailOption defaultOption = emailOptionRepository.getDefaultOption();

        return MailerBuilder
                .withSMTPServer(
                        defaultOption.getHost(),
                        defaultOption.getPort(),
                        defaultOption.getUsername(),
                        defaultOption.getPassword())
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();
    }

}
