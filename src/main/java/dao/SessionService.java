package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;

@Singleton
public class SessionService {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private static SqlSessionFactory sessionFactory;

    static {

        try (InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG)) {
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private SessionService() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static SqlSession getSession() {
        return sessionFactory.openSession();
    }

}
