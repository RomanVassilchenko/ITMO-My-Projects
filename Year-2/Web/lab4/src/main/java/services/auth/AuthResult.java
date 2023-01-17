package services.auth;

import lombok.Getter;

@Getter
public class AuthResult {
    private final String errorMessage;
    private final String token;
    private final boolean successful;

    private AuthResult(String errorMessage, String token, boolean successful) {
        this.errorMessage = errorMessage;
        this.token = token;
        this.successful = successful;
    }

    public static AuthResult message(String message) {
        return new AuthResult(message, null, false);
    }

    public static AuthResult token(String token) {
        return new AuthResult(null, token, true);
    }
}
