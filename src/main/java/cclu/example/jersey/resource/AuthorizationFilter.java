package cclu.example.jersey.resource;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cclu.example.jersey.data.DataStore;
import cclu.example.jersey.data.User;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
    public static final String BASIC_AUTHN = "Authorization";
    public static final String AUTHN_SCHEME = "Basic";
    
    @Context
    ResourceInfo resInfo;

    @Override
    public void filter(ContainerRequestContext ctxt) throws IOException {
        String authnText = ctxt.getHeaderString(BASIC_AUTHN);
        if (!authenticate(authnText, ctxt)) {
            throw new RuntimeException("Authetnication failed");
        }
        Method method = resInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) return;
        String[] roles = method.getAnnotation(RolesAllowed.class).value();
        SecurityContext seContext = ctxt.getSecurityContext();
        for (String role : roles) {
            if (seContext.isUserInRole(role)) return;
        }
        throw new RuntimeException("Not Authorized");
    }
    
    private boolean authenticate(String authnText, ContainerRequestContext ctxt) {
        authnText = Base64.decodeAsString(authnText.replace(AUTHN_SCHEME, "").trim());
        log.debug(authnText);
        String[] principal = authnText.split(":");
        User user = DataStore.users.get(principal[0]);
        if (user != null && user.getPassword().equals(principal[1])) {
            ctxt.setSecurityContext(new CustomSecurityContext(user));
            return true;
        }
        return false;
    }
}
