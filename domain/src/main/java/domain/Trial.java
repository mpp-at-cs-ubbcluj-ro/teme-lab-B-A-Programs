package domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Trial")
public class Trial extends Identifiable<Long> {
    private String name;
    private AgeGroup ageGroup;
    private List<Child> enrolledChildren = new ArrayList<>();

    public Trial() {}

    public Trial(Long id, String name, AgeGroup ageGroup) {
        this.setId(id);
        this.name = name;
        this.ageGroup = ageGroup;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group")
    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "Child_Trial",
            joinColumns = @JoinColumn(name = "trial_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    public List<Child> getEnrolledChildren() {
        return enrolledChildren;
    }

    public void setEnrolledChildren(List<Child> enrolledChildren) {
        this.enrolledChildren = enrolledChildren;
    }

    @Override
    public String toString() {
        return "Trial{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", ageGroup=" + ageGroup +
                '}';
    }
}
