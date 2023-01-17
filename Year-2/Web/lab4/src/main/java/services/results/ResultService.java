package services.results;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import org.jetbrains.annotations.NotNull;
import jakarta.ejb.*;
import jakarta.transaction.Transactional;

import beans.User;
import beans.Result;
import database.ResultStore;
import database.UserStore;
import exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Stateless
@Transactional
public class ResultService {
    @EJB
    private ResultStore resultStore;
    @EJB
    private UserStore userStore;

    public User loadUser(@NotNull String username) throws UserNotFoundException {
        Optional<User> optionalUser = userStore.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("given username " + username + " not found in USERS");
        }
    }

    public void add(@NotNull Result result, @NotNull String username) throws UserNotFoundException {
        resultStore.save(result, username);
    }

    public void clear(@NotNull String username) {
        resultStore.clear(username);
    }

    public List<Result> getAllByOwnerUsername(@NotNull String username) {
        return resultStore.findAllByOwner(username);
    }

    public String getAllJSON(@NotNull String username) {
        List<Result> results = getAllByOwnerUsername(username);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Result result : results) {
            arrayBuilder.add(result.toJSONObject());
        }
        return arrayBuilder.build().toString();
    }
}
