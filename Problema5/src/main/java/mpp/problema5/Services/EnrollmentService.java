package mpp.problema5.Services;

import mpp.problema5.Domain.AgeGroup;
import mpp.problema5.Domain.Trial;
import mpp.problema5.Domain.Child;
import mpp.problema5.Repository.ChildRepository;
import mpp.problema5.Repository.TrialRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class EnrollmentService {
    private ChildRepository childRepository;
    private TrialRepository trialRepository;

    public EnrollmentService(ChildRepository childRepository, TrialRepository trialRepository) {
        this.childRepository = childRepository;
        this.trialRepository = trialRepository;
    }

    public int getChildEnrollmentsNumber(Child child) {
        Child existingChild = childRepository.findByCnp(child.getCNP());
        if (existingChild == null) {
            return 0;
        }
        return childRepository.getTrialsForChild(existingChild).size();
    }

    public void enrollChild(long trialId, Child child) throws Exception {
        Trial trial = trialRepository.getById(trialId);
        Child existingChild = childRepository.findByCnp(child.getCNP());

        if (!isChildValidForTrial(child, trial.getAgeCategory())) {
            throw new Exception("Child does not meet the age requirements for this trial.");
        }

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

            trialRepository.addChild(trialId, existingChild);
            return;
        }

        childRepository.add(child);
        Child addedChild = childRepository.findByCnp(child.getCNP());
        trialRepository.addChild(trialId, addedChild);
    }

    private boolean isChildValidForTrial(Child child, AgeGroup ageGroup) {
        LocalDate birthDate = getBirthDateFromCNP(child.getCNP());
        int childAge = calculateAge(birthDate);

        switch (ageGroup) {
            case SIXEIGHT:
                return childAge >= 6 && childAge <= 8;
            case NINEELEVEN:
                return childAge >= 9 && childAge <= 11;
            case TWELVEFIFTEEN:
                return childAge >= 12 && childAge <= 15;
            default:
                return false;
        }
    }

    private LocalDate getBirthDateFromCNP(String cnp) {
        // Extract year, month, day from the CNP (the first 7 digits)
        String yearPart = cnp.substring(1, 3); // Two-digit year
        String monthPart = cnp.substring(3, 5); // Two-digit month
        String dayPart = cnp.substring(5, 7); // Two-digit day

        // The year in CNP can be in a different century, so we need to adjust for that
        int year = Integer.parseInt(yearPart);
        int month = Integer.parseInt(monthPart);
        int day = Integer.parseInt(dayPart);

        // Adjust for century based on the first digit of the CNP (1 or 2 means 1900s, 3 or 4 means 2000s)
        int century = Integer.parseInt(cnp.substring(0, 1));
        if (century == 1 || century == 2) {
            year += 1900;
        } else {
            year += 2000;
        }

        return LocalDate.of(year, month, day);
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
