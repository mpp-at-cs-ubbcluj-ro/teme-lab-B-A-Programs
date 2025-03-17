import MPP.Domain.AgeGroup;
import MPP.Domain.Child;
import MPP.Domain.Trial;
import MPP.Repository.ChildDBRepository;
import MPP.Repository.TrialDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        TrialDBRepository trialDBRepository = new TrialDBRepository(props);
                trialDBRepository.add(new Trial(0L, "Crosetat", AgeGroup.NINEELEVEN, new ArrayList<Child>()));

        List<Trial> trials = trialDBRepository.getAll();
        for (Trial trial : trials) {
            System.out.println(trial);
        }
    }
}