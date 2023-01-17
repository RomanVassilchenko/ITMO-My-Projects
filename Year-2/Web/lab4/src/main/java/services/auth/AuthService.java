package services.auth;

import org.jetbrains.annotations.NotNull;
import jakarta.ejb.*;
import org.apache.commons.codec.digest.DigestUtils;

import beans.User;
import database.UserStore;

import java.util.Optional;

@Stateless
public class AuthService {
    @EJB
    private UserStore users;
    @EJB
    private TokenService tokenService;

    /**
     * Checks if username exists, checks password and returns token if everything is fine.
     *
     * @param username username to check
     * @param password password to check
     * @return AuthResult with token if correct / AuthResult with errorMessage if not
     */
    public AuthResult login(@NotNull String username, @NotNull String password) {
        final Optional<User> optionalUser = users.findByUsername(username);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getPassword().equals(encode(password))) {
                return AuthResult.token(tokenService.generate(username));
            } else {
                return AuthResult.message("WRONG_PASSWORD");
            }
        } else {
            return AuthResult.message("USER_NOT_FOUND");
        }
    }

    /**
     * Checks if user already exists; if not, adds them to database and returns token.
     *
     * @param username username to register
     * @param password password to register
     * @return AuthResult with token if successful / AuthResult with errorMessage if not
     */
    public AuthResult register(@NotNull String username, @NotNull String password) {
        if (users.checkIfUserExists(username)) {
            return AuthResult.message("USER_ALREADY_EXISTS");
        } else {
            users.save(new User(username, encode(password)));
            return AuthResult.token(tokenService.generate(username));
        }
    }

    /**
     * Accepts token and decodes username from it.
     * Also verifies the token so the action by user who sent this token is authorized.
     *
     * @param token token to decode.
     * @return username from token if the token is valid.
     */
    public Optional<String> getUsernameByToken(String token) {
        return tokenService.verify(token);
    }

    private static String encode(String s) {
        return DigestUtils.sha256Hex(s);
    }
}
