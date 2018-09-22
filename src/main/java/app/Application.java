package app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import scheduler.Scheduler;

@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "controllers"
})
public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector((Module) binder -> {
        });

        injector.getInstance(Scheduler.class).schedule();

        SpringApplication.run(Application.class, args);
    }
}
