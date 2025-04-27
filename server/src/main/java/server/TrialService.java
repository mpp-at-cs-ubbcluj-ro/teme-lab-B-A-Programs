package server;

import domain.Child;
import domain.DTO.ChildDTO;
import domain.DTO.TrialDTO;
import domain.Trial;
import repository.ChildRepository;
import repository.TrialRepository;
import services.ITrialService;

import java.util.ArrayList;
import java.util.List;

public class TrialService implements ITrialService {
    private TrialRepository repository;
    private ChildRepository childRepository;

    public TrialService(TrialRepository repository, ChildRepository childRepository) {
        this.repository = repository;
        this.childRepository = childRepository;
    }

    public List<TrialDTO> getTrials() {
        List<Trial> trials = repository.getAll();
        List<Child> children = new ArrayList<>();
        List<TrialDTO> trialDTOs = new ArrayList<>();
        for (Trial trial : trials) {
            List<Child> trialChildren = trial.getEnrolledChildren();
            List<ChildDTO> childDTOs = new ArrayList<>();
            for (Child child : trialChildren) {
                childDTOs.add(new ChildDTO(child.getId(), child.getCNP(), child.getName(), childRepository.getTrialsForChild(child).size()));
            }
            System.out.println(trial.getAgeGroup());
            trialDTOs.add(new TrialDTO(
                    trial.getId(),
                    trial.getName(),
                    trial.getAgeGroup(),
                    childDTOs
            ));
        }
        return trialDTOs;
    }
}
