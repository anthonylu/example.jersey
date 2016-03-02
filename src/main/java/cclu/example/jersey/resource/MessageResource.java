package cclu.example.jersey.resource;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cclu.example.jersey.data.DataStore;
import cclu.example.jersey.data.Message;


@Path("messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
    private static final Logger log = LoggerFactory.getLogger(MessageResource.class);
    @Context
    SecurityContext secContext;
    
    @GET
    @PermitAll
    public Response getMessages() {
        ArrayList<Message> msgs = getUserMessageList();
        return Response.ok(msgs).build();
    }
    
    @GET
    @Path("{id}")
    @PermitAll
    public Response getMessage(@PathParam("id") Integer id) {
        ArrayList<Message> msgs = getUserMessageList();
        Message msg = msgs.get(id);
        return Response.ok(msg).build();
    }
    
    @POST
    @PermitAll
    public Response addMessage(@Valid@NotNull Message msg) {
        ArrayList<Message> msgs = getUserMessageList();
        msg.setId(msgs.size());
        msgs.add(msg);

        return Response.ok(msg).build();
    }
    
    @DELETE
    @Path("{id}")
    @PermitAll
    public Response deleteMessage(@PathParam("id") Integer id) {
        ArrayList<Message> msgs = getUserMessageList();
        Message msg = msgs.get(id);
        msg.setDeleted();
        
        return Response.ok(msg).build();
    }

    private String getUserEmail() {
        return secContext.getUserPrincipal().getName();
    }
    
    private ArrayList<Message> getUserMessageList() {
        String email = getUserEmail();
        log.debug("email from security context: {}", email);
        ArrayList<Message> msgs = DataStore.messages.get(email);
        if (msgs == null) {
            msgs = new ArrayList<>();
            DataStore.messages.put(email, msgs);
        }
        return msgs;
    }
}
