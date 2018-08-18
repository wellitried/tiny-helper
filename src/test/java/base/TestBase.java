package base;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import email.MailSenderTest;
import org.junit.Before;

public class TestBase {

    private Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MailSenderTest.class);
        }
    });

    @Before
    public void setup() {
        injector.injectMembers(this);
    }
}