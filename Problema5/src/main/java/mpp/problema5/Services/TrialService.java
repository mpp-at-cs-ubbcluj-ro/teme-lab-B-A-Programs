package mpp.problema5.Services;

import mpp.problema5.Domain.Trial;
import mpp.problema5.Repository.TrialRepository;

import java.util.List;

public class TrialService {
    private TrialRepository repository;

    public TrialService(TrialRepository repository) {
        this.repository = repository;
    }

    public List<Trial> getTrials() {
        return repository.getAll();
    }
}
