package beans;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Result> usersResults;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
