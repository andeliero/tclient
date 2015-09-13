package it.tclient.web;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
public class Login {

    @Autowired
    private HttpServletRequest session;

    @RequestMapping("/login")
    public String login() {
        String users = session.getRemoteUser();
        if(users != null)
            return "redirect:/index";
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        String endPoint = "http://test-view.4me.it/api/xsso/resources/identitymanager/";
        String clientId = "test";
        Object tokenId = session.getSession().getAttribute("tokenId");
        Client client = Client.create();
        WebResource webResource = client.resource(endPoint);
        Form form = new Form();
        form.add("clientId", clientId);
        ClientResponse response = webResource.path("/logout").header("X-TOKENID",tokenId).post(ClientResponse.class, form);
        String risp = response.getEntity(String.class);
        session.getSession().removeAttribute("tokenId");
        return "redirect:/deeplogout";
    }

}
