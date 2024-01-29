package it.letscode.phoneapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class ScraperRunner {
    public static void main(String[] args) {
        // Tworzenie kontekstu Spring z konfiguracją głównej aplikacji
        ConfigurableApplicationContext context = SpringApplication.run(PhoneApiApplication.class, args);

        // Pobranie bean'a Scrapper i uruchomienie metody scrape()
        Scrapper scrapper = context.getBean(Scrapper.class);
        scrapper.scrape();

        // Zamknięcie kontekstu Spring
        context.close();
    }
}
