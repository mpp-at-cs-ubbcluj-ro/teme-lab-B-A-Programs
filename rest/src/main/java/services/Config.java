package services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.TrialDBRepository;
import repository.TrialRepository;

@Configuration
public class Config {
    @Bean
    TrialRepository trialRepository() {
        return new TrialDBRepository();
    }
}
