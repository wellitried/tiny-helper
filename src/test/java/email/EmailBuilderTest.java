package email;

import dao.repositories.EmailOptionRepository;
import email.emailoption.EmailOption;
import message.Message;
import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;

import static org.junit.Assert.assertEquals;

public class EmailBuilderTest extends EmailTestBase {

    @Test
    public void buildEmail() {
        Message message = getMessageMock();
        EmailOptionRepository emailOptionRepository = getEmailOptionRepositoryMock();
        EmailOption defaultEmailOption = getActualDefaultEmailOption();

        EmailBuilder emailBuilder = new EmailBuilder(emailOptionRepository);

        Email email = emailBuilder.buildEmail(message.getRecipient(), message.getSubject(), message.getText());
        assertEquals(message.getSubject(), email.getSubject());
        assertEquals(message.getText(), email.getHTMLText());

        Recipient recipient = email.getRecipients().get(0);
        assertEquals(message.getRecipient(), recipient.getAddress());

        Recipient fromRecipient = email.getFromRecipient();
        assertEquals(defaultEmailOption.getFromAddress(), fromRecipient.getAddress());
        assertEquals(defaultEmailOption.getFromName(), fromRecipient.getName());
    }

}