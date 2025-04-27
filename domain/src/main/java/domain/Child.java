package domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Child")
public class Child extends Identifiable<Long> {
    private String CNP;
    private String name;
    private List<Trial> enrolledTrials;

    public Child() {}

    public Child(Long id, String CNP, String name) {
        this.setId(id);
        this.CNP = CNP;
        this.name = name;
    }

    @Column(name = "CNP", nullable = false, unique = true)
    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "enrolledChildren")
    public List<Trial> getEnrolledTrials() {
        return enrolledTrials;
    }

    public void setEnrolledTrials(List<Trial> enrolledTrials) {
        this.enrolledTrials = enrolledTrials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return Objects.equals(CNP, child.CNP) && Objects.equals(getId(), child.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(CNP, this.getId()); // Fixed hashCode to use CNP and id
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + this.getId() +
                ", CNP='" + CNP + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}