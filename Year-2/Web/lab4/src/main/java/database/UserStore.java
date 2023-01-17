package database;

import jakarta.ejb.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import beans.User;
import java.util.Optional;

@Stateless
@Transactional
public class UserStore {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    public Optional<User> findByUsername(String username) {
        User user = entityManager.find(User.class, username);
        return Optional.ofNullable(user);
    }

    public boolean checkIfUserExists(String username) {
        User user = entityManager.find(User.class, username);
        return user != null;
    }
}