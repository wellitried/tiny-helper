package dao.mappers;

import dao.SessionService;
import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailOptionMapperTest {

    private SqlSession session;
    private EmailOptionMapper emailOptionMapper;
    private EmailOption emailOption;

    @Before
    public void setUp() {
        session = SessionService.getSession();
        emailOptionMapper = session.getMapper(EmailOptionMapper.class);

        insertNewEmailOption();
    }

    @After
    public void tearDown() {
        deleteEmailOption();
        session.close();
    }

    @Test
    public void create() {
        assertNotNull(emailOption.getId());
    }

    @Test
    public void selectById() {
        EmailOption createdEmailOption = emailOptionMapper.selectById(emailOption.getId());

        assertNotNull(createdEmailOption);
    }

    @Test
    public void selectByType() {
        EmailOption createdEmailOption = emailOptionMapper.selectByType(EmailOptionType.ADDITIONAL);

        assertEquals(EmailOptionType.ADDITIONAL, createdEmailOption.getType());
    }

    @Test
    public void update() {
        emailOption.setPort(100);

        emailOptionMapper.update(emailOption);
        session.commit();

        EmailOption createdEmailOption = emailOptionMapper.selectById(emailOption.getId());

        assertEquals((Integer) 100, createdEmailOption.getPort());
    }

    @Test
    public void delete() {
        deleteEmailOption();

        EmailOption createdEmailOption = emailOptionMapper.selectById(emailOption.getId());

        assertNull(createdEmailOption);
    }

    private void insertNewEmailOption() {
        emailOption = new EmailOption();
        emailOption.setType(EmailOptionType.ADDITIONAL);
        emailOption.setFromAddress("test@test.com");
        emailOption.setFromName("test");
        emailOption.setHost("smtp.test.com");
        emailOption.setPort(500);
        emailOption.setUsername("username");
        emailOption.setPassword("password");

        assertNull(emailOption.getId());

        emailOptionMapper.create(emailOption);
        session.commit();

        assertNotNull(emailOption.getId());
    }

    private void deleteEmailOption() {
        emailOptionMapper.delete(emailOption.getId());
        session.commit();
    }
}