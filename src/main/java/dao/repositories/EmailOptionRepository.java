package dao.repositories;

import dao.SessionService;
import dao.mappers.EmailOptionMapper;
import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
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
        try (SqlSession session = sessionService.getSession()) {
            EmailOptionMapper mapper = session.getMapper(EmailOptionMapper.class);

            EmailOption emailOption = mapper.selectByType(EmailOptionType.DEFAULT);

            return emailOption;
        }
    }
}
