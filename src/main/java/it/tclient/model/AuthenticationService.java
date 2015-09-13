package it.tclient.model;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationService implements AuthenticationProvider {

    String endPoint = "http://test-view.4me.it/api/xsso/resources/identitymanager/";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //prendo le variabili dai parametri della form
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String clientId = "test";

        Client client = Client.create();
        WebResource webResource = client.resource(endPoint);
        Form form = new Form();
        form.add("clientId", clientId);
        form.add("username", username);
        form.add("password", password);
        ClientResponse response = webResource.path("/login").post(ClientResponse.class, form);
        String token = response.getEntity(String.class);
        //System.out.println(token);
        /*MResponseFindContents response = webResource.path("/login")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(MResponseFindContents.class, form);
        List<MContentSummary> contents = response.getContents();
        for(MContentSummary i : contents) {
            System.out.println(i.toString());
        }*/
        boolean userIsRegistered = false;
        if (!token.equals("Wrong Credentials")) {
            userIsRegistered = true;
        }
        if(userIsRegistered) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            request
                    .getSession()
                    .setAttribute("tokenId", token);
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        } else {
            throw new BadCredentialsException("Le credenziali non sono registrate ne sistema");
        }
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
