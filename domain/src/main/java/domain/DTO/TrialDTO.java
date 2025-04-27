package domain.DTO;

import domain.AgeGroup;

import java.util.List;

public class TrialDTO extends Entity<Long> {
    private String name;
    private AgeGroup ageGroup;
    private List<ChildDTO> enrolledChildren;

    public TrialDTO(Long id, String name, AgeGroup ageGroup, List<ChildDTO> enrolledChildren) {
        super(id);
        this.name = name;
        this.ageGroup = ageGroup;
        this.enrolledChildren = enrolledChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<ChildDTO> getEnrolledChildren() {
        return enrolledChildren;
    }

    public void setEnrolledChildren(List<ChildDTO> enrolledChildren) {
        this.enrolledChildren = enrolledChildren;
    }

    @Override
    public String toString() {
        return "TrialDTO{" +
                "name='" + name + '\'' +
                ", ageGroup=" + ageGroup +
                ", enrolledChildren=" + enrolledChildren +
                '}';
    }
}
