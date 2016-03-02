package cclu.example.jersey.resource;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import cclu.example.jersey.data.User;

public class CustomSecurityContext implements SecurityContext {
    private Principal principal;
    private String role;
    
    private class UserPrincipal implements Principal {
        private String name;
        
        UserPrincipal(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
    }
    
    public CustomSecurityContext(User usr) {
        principal = new UserPrincipal(usr.getEmail());
        
        switch(usr.getRole()) {
            case Admin:
                role = "Admin";
                break;
            case GeneralUser:
                role = "GeneralUser";
                break;
        }
    }

    @Override
    public String getAuthenticationScheme() {
        return "Basic";
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equals(this.role);
    }

}
