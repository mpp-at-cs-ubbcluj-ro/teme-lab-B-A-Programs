package MPP.Domain;

import java.util.ArrayList;

public class Trial extends Entity<Long> {
    private String name;
    private AgeGroup ageGroup;
    private ArrayList<Child> enrolledChildren;

    public Trial(Long id, String name, AgeGroup ageGroup, ArrayList<Child> enrolledChildren) {
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

    public ArrayList<Child> getEnrolledChildren() {
        return enrolledChildren;
    }

    public void setEnrolledChildren(ArrayList<Child> enrolledChildren) {
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
