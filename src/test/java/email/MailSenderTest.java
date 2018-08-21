package email;

import dao.SessionService;
import dao.mappers.EmailOptionMapper;
import dao.repositories.EmailOptionRepository;
import dao.repositories.MessageRepository;
import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import message.Message;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MailSenderTest {

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

        EmailOption defaultEmailOption = getDefaultEmailOption();
        when(emailOptionRepository.getDefaultOption()).thenReturn(defaultEmailOption);

        return emailOptionRepository;
    }

    private EmailOption getDefaultEmailOption() {
        SqlSession session = SessionService.getSession();
        EmailOption emailOption = session.getMapper(EmailOptionMapper.class).selectByType(EmailOptionType.DEFAULT);
        session.close();

        return emailOption;
    }
}