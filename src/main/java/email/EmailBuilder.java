package email;

import dao.repositories.EmailOptionRepository;
import email.emailoption.EmailOption;
import org.simplejavamail.email.Email;

import javax.inject.Inject;

public class EmailBuilder {

    private final EmailOptionRepository emailOptionRepository;

    @Inject
    EmailBuilder(EmailOptionRepository emailOptionRepository) {
        this.emailOptionRepository = emailOptionRepository;
    }

    public Email buildEmail(String recipient, String subject, String message) {

        EmailOption defaultOption = emailOptionRepository.getDefaultOption();

        String fromAddress = defaultOption.getFromAddress();
        String fromName = defaultOption.getFromName();

        return org.simplejavamail.email.EmailBuilder.startingBlank()
                .from(fromName, fromAddress)
                .to(recipient)
                .withHTMLText(message)
                .withSubject(subject)
                .buildEmail();
    }
}
