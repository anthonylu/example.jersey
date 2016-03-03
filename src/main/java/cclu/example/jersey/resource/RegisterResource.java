package cclu.example.jersey.resource;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cclu.example.jersey.data.DataStore;
import cclu.example.jersey.data.Role;
import cclu.example.jersey.data.User;

@Path("registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterResource {
    @POST
    @RolesAllowed("Admin")
    public Response register(@Valid@NotNull User user) {
        if (DataStore.users.containsKey(user.getEmail())) {
            return Response.status(400).build();
        } else {
            user.setRole(Role.GeneralUser);
            user.setId(DataStore.userSeq.getAndIncrement());
            DataStore.users.put(user.getEmail(), user);
            DataStore.idToUser.put(user.getId(), user);
            return Response.ok(user).build();
        }
    }

}
