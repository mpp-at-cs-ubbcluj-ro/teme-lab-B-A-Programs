package services;

import repository.TrialDBRepository;
import repository.TrialRepository;

@Bean
TrialRepository trialRepository() {
    return new TrialDBRepository();
}

