package repository;

import domain.Child;
import domain.Trial;

public interface TrialRepository extends Repository<Trial, Long> {
    void addChild(Long trialId, Child child);
}
