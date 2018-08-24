package email;

import dao.SessionService;
import dao.mappers.EmailOptionMapper;
import dao.repositories.EmailOptionRepository;
import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import message.Message;
import org.apache.ibatis.session.SqlSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmailTestBase {

    EmailOptionRepository getEmailOptionRepositoryMock() {
        EmailOptionRepository emailOptionRepository = mock(EmailOptionRepository.class);

        EmailOption defaultEmailOption = getActualDefaultEmailOption();
        when(emailOptionRepository.getDefaultOption()).thenReturn(defaultEmailOption);

        return emailOptionRepository;
    }

    EmailOption getActualDefaultEmailOption() {
        SqlSession session = SessionService.getSession();
        EmailOption emailOption = session.getMapper(EmailOptionMapper.class).selectByType(EmailOptionType.DEFAULT);
        session.close();

        return emailOption;
    }

    Message getMessageMock() {
        Message message = mock(Message.class);
        when(message.getRecipient()).thenReturn("test@test.com");
        when(message.getText()).thenReturn("test");

        return message;
    }
}
