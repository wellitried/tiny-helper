package email;

import base.TestBase;
import dao.SessionService;
import dao.mappers.EmailOptionMapper;
import dao.repositories.EmailOptionRepository;
import dao.repositories.MessageRepository;
import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import message.Message;
import org.junit.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

public class MailSenderTest extends TestBase {

    @Inject
    private SessionService sessionService;

    @Test
    public void sendMessageTest() {

        EmailOptionRepository emailOptionRepository = getEmailOptionRepositoryMock();
        Message message = getMessageMock();
        MessageRepository messageRepository = getMessageRepositoryMock();

        MailSender mailSender = new MailSender(emailOptionRepository, messageRepository, new EmailBuilder(emailOptionRepository));

        mailSender.sendMessage(message);
        verify(messageRepository).markMessageAsSent(message);
    }

    private MessageRepository getMessageRepositoryMock() {
        return mock(MessageRepository.class);
    }

    private Message getMessageMock() {
        Message message = mock(Message.class);
        when(message.getRecipient()).thenReturn("test@test.com");
        when(message.getText()).thenReturn("test");

        return message;
    }

    private EmailOptionRepository getEmailOptionRepositoryMock() {
        EmailOptionRepository emailOptionRepository = mock(EmailOptionRepository.class);
        when(emailOptionRepository.getDefaultOption()).thenReturn(getDefaultEmailOption());

        return emailOptionRepository;
    }

    private EmailOption getDefaultEmailOption() {
        return sessionService.getSession().getMapper(EmailOptionMapper.class).selectByType(EmailOptionType.DEFAULT);
    }
}