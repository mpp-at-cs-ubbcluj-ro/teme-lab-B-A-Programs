package mpp.problema5.Repository;

import mpp.problema5.Domain.Child;
import mpp.problema5.Domain.Trial;

public interface TrialRepository extends Repository<Trial, Long> {
    void addChild(Long trialId, Child child);
}
