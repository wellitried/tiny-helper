package dao.repositories;

import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmailOptionRepositoryTest {

    @Test
    public void getDefaultOption() {
        EmailOptionRepository emailOptionRepository = new EmailOptionRepository();
        EmailOption defaultOption = emailOptionRepository.getDefaultOption();

        assertNotNull(defaultOption);
        assertEquals(EmailOptionType.DEFAULT, defaultOption.getType());
    }
}