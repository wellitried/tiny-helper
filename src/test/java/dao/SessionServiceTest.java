package dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SessionServiceTest {

    @Test
    public void getSession() {
        SqlSession session = SessionService.getSession();

        assertNotNull(session);
        assertNotNull(session.getConnection());
        assertNotNull(session.getConfiguration());

        session.close();
    }
}