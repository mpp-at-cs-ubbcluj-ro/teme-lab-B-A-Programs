package services;

import domain.DTO.TrialDTO;

import java.util.List;

public interface ITrialService {
    public List<TrialDTO> getTrials();
}