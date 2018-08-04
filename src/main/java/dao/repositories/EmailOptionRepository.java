package dao.repositories;

import dao.SessionService;
import dao.mappers.EmailOptionMapper;
import email.emailoption.EmailOption;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmailOptionRepository {

    private final SessionService sessionService;


    @Inject
    public EmailOptionRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    public EmailOption getDefaultOption() {

        EmailOption emailOption = null;

        try (SqlSession session = sessionService.getSession()) {
            EmailOptionMapper mapper = session.getMapper(EmailOptionMapper.class);

            emailOption = mapper.selectByType(EmailOption.EmailOptionType.DEFAULT);
        }

        return emailOption;
    }
}
