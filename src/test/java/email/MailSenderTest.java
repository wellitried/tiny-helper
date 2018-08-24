package email;

import dao.repositories.EmailOptionRepository;
import dao.repositories.MessageRepository;
import message.Message;
import org.junit.Test;
import org.simplejavamail.email.Email;

import static org.mockito.Mockito.*;

public class MailSenderTest extends EmailTestBase {

    @Test
    public void sendMessageTest() {

        EmailOptionRepository emailOptionRepository = getEmailOptionRepositoryMock();
        Message message = getMessageMock();
        MessageRepository messageRepository = getMessageRepositoryMock();
        EmailBuilder emailBuilder = getEmailBuilderMock(message);

        MailSender mailSender = new MailSender(emailOptionRepository, messageRepository, emailBuilder);

        mailSender.sendMessage(message);
        verify(emailBuilder).buildEmail(message.getRecipient(), message.getSubject(), message.getText());
        verify(messageRepository).markMessageAsSent(message);
    }

    private MessageRepository getMessageRepositoryMock() {
        return mock(MessageRepository.class);
    }

    private EmailBuilder getEmailBuilderMock(Message message) {
        EmailBuilder emailBuilder = mock(EmailBuilder.class);
        Email actualEmail = getActualEmail(message);
        when(emailBuilder.buildEmail(message.getRecipient(), message.getSubject(), message.getText())).thenReturn(actualEmail);

        return emailBuilder;
    }

    private Email getActualEmail(Message message) {
        EmailOptionRepository emailOptionRepository = getEmailOptionRepositoryMock();
        EmailBuilder emailBuilder = new EmailBuilder(emailOptionRepository);

        return emailBuilder.buildEmail(message.getRecipient(), message.getSubject(), message.getText());
    }
}