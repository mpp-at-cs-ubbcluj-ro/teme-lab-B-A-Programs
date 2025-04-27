package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User extends Identifiable<Long> {
    private String username;
    private String password;

    public User() {}

    public User(Long id, String username, String password) {
        this.setId(id);
        this.username = username;
        this.password = password;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
