package cclu.example.jersey.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import cclu.example.jersey.data.DataStore;
import cclu.example.jersey.data.User;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Context
    SecurityContext secContext;
    
    @GET
    @PermitAll
    @Path("{id}")
    public Response getUser(@PathParam("id") Integer id) {
    	User usr = DataStore.idToUser.get(id);
    	if (usr == null) return Response.status(404).build();
    	String email = usr.getEmail();
        if (email.equals(getUserEmail())) {
            User user = DataStore.users.get(email);
            if (user == null) {
                return Response.status(404).build();
            } else {
                return Response.ok(user).build();
            }
        }
        
        return Response.status(401).build();
    }

    @PUT
    @PermitAll
    @Path("{id}")
    public Response modifyUser(@PathParam("id") Integer id, User user) {
    	User usr = DataStore.idToUser.get(id);
    	if (usr == null) return Response.status(404).build();
    	String email = usr.getEmail();
        if (email.equals(getUserEmail())) {
            User old = DataStore.users.get(email);
            if (old == null) {
                return Response.status(404).build();
            } else {
                old.setFirstName(user.getFirstName());
                old.setLastName(user.getLastName());
                return Response.ok(old).build();
            }
        }

        return Response.status(401).build();
    }

    private String getUserEmail() {
        return secContext.getUserPrincipal().getName();
    }
}
