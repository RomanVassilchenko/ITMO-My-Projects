package database;

import jakarta.ejb.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import beans.User;
import beans.Result;
import java.util.List;
import services.results.ResultService;
import exceptions.UserNotFoundException;

@Stateless
@Transactional
public class ResultStore {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ResultService resultService;

    //  By User object

    public void clear(User user) {
        entityManager.createQuery("delete from Result result where result.owner = :owner")
                .setParameter("owner", user)
                .executeUpdate();
    }

    public void save(Result result) {
        entityManager.persist(result);
        entityManager.flush();
    }

    public List<Result> findAllByOwner(User user) {
        String query = "select result from Result result where result.owner = :owner";
        return entityManager.createQuery(query, Result.class)
                .setParameter("owner", user)
                .getResultList();
    }

    public void clear(String username) {
        entityManager.createQuery("delete from Result result where result.owner.username = :username")
                .setParameter("username", username)
                .executeUpdate();
    }

    public void save(Result result, String username) throws UserNotFoundException {
        User user = resultService.loadUser(username);
        result.setOwner(user);
        entityManager.persist(result);
        entityManager.flush();
    }

    public List<Result> findAllByOwner(String username) {
        return entityManager.createQuery("select result from Result result where result.owner.username = :username", Result.class)
                .setParameter("username", username)
                .getResultList();
    }
}