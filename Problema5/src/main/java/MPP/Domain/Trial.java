package MPP.Domain;

import java.util.List;

public class Trial extends Entity<Long> {
    private String name;
    private AgeGroup ageGroup;
    private List<Child> enrolledChildren;

    public Trial(Long id, String name, AgeGroup ageGroup, List<Child> enrolledChildren) {
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

    public AgeGroup getAgeCategory() {
        return ageGroup;
    }

    public void setAgeCategory(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<Child> getEnrolledChildren() {
        return enrolledChildren;
    }

    public void setEnrolledChildren(List<Child> enrolledChildren) {
        this.enrolledChildren = enrolledChildren;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", ageCategory=" + ageGroup.toString() +
                '}';
    }
}
