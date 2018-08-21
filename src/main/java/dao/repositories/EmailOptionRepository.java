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

    private EmailOption defaultOption;

    @Inject
    public EmailOptionRepository() {
    }

    public EmailOption getDefaultOption() {
        if (defaultOption == null) {
            SqlSession session = SessionService.getSession();
            EmailOptionMapper mapper = session.getMapper(EmailOptionMapper.class);

            defaultOption = mapper.selectByType(EmailOptionType.DEFAULT);

            session.close();
        }

        return defaultOption;
    }
}
