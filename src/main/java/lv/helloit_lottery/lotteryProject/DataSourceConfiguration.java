package lv.helloit_lottery.lotteryProject;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Lottery.class)
                .configure()
                .buildSessionFactory();

    }
}