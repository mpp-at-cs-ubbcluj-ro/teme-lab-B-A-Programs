package mpp.problema5.Services;

import mpp.problema5.Domain.Trial;
import mpp.problema5.Domain.Child;
import mpp.problema5.Repository.ChildRepository;
import mpp.problema5.Repository.TrialRepository;

import java.util.List;

public class EnrollmentService {
    private ChildRepository childRepository;
    private TrialRepository trialRepository;

    public EnrollmentService(ChildRepository childRepository, TrialRepository trialRepository) {
        this.childRepository = childRepository;
        this.trialRepository = trialRepository;
    }

    public void enrollChild(long trialId, Child child) throws Exception {
        Child existingChild = childRepository.getById(child.getId());
        if (existingChild != null) {
            List<Trial> trials = childRepository.getTrialsForChild(existingChild);

            if (trials.size() >= 2) {
                throw new Exception("Child is already enrolled in 2 Trials");
            }

            if (trials.size() == 1) {
                if (trials.getFirst().getId() == trialId) {
                    throw new Exception("Child is already enrolled in trial " + trials.getFirst().getName());
                }
            }

            trialRepository.addChild(trialId, child);
            return;
        }

        childRepository.add(child);
        trialRepository.addChild(trialId, child);
    }
}
