package rest;

import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.json.Credentials;

import services.auth.AuthResult;
import services.auth.AuthService;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @EJB
    private AuthService authService;

    @POST
    @Path("/login")
    public Response login(@NotNull(message = "MISSING_CREDENTIALS") @Valid Credentials credentials) {
        AuthResult result = authService.login(credentials.getUsername(), credentials.getPassword());
        if (result.isSuccessful()) {
            return Response.ok(tokenJSON(result.getToken())).build();
        } else {
            return Response.status(FORBIDDEN).entity(result.getErrorMessage()).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(@NotNull(message = "MISSING_CREDENTIALS") @Valid Credentials credentials) {
        AuthResult result = authService.register(credentials.getUsername(), credentials.getPassword());
        if (result.isSuccessful()) {
            return Response.ok(tokenJSON(result.getToken())).build();
        } else {
            return Response.status(FORBIDDEN).entity(result.getErrorMessage()).build();
        }
    }

    private String tokenJSON(String token) {
        return Json.createObjectBuilder().add("token", token).build().toString();
    }
}