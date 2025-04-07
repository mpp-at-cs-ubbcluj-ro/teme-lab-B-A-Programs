package domain.DTO;

import domain.Entity;

public class ChildDTO extends Entity<Long> {
    private String CNP;
    private String name;
    private Integer enrollments;

    public ChildDTO(Long id, String CNP, String name, Integer enrollments) {
        super(id);
        this.CNP = CNP;
        this.name = name;
        this.enrollments = enrollments;
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

    public Integer getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Integer enrollments) {
        this.enrollments = enrollments;
    }
}
