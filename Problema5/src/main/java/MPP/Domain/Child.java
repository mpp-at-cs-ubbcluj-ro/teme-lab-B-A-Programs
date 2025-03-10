package MPP.Domain;

import java.util.Objects;

public class Child extends Entity<Long> {
    private String CNP;
    private String name;

    public Child(Long id, String CNP, String name) {
        super(id);
        this.CNP = CNP;
        this.name = name;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Objects.hashCode(CNP + getId());
    }

    @Override
    public String toString() {
        return "Child{" +
                "ID=" + getId() +
                "CNP='" + CNP + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
