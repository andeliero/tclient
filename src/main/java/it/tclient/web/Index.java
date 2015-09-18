package it.tclient.web;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import it.newvision.nvp.webtv.services.model.delivery.MContentWall;
import it.newvision.nvp.webtv.services.model.delivery.MResponseContentListResult;
import it.newvision.nvp.xcontents.services.model.content.MResponseContentDetail;
import it.tclient.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Controller
@EnableAutoConfiguration
public class Index {

    @Autowired
    private HttpServletRequest session;

    @RequestMapping(value={"/","/index","/home"})
    public String index(Model model) {
        String endPoint = "http://test-view.4me.it/api/xcontents/resources/contentlist/";
        String clientId = "test";
        Object tokenId = session.getSession().getAttribute("tokenId");
        String categoryId = "41080975-7184-42f6-bcc5-84b477b4ef9d";
        Client client = Client.create();
        WebResource webResource = client.resource(endPoint);
        Form form = new Form();
        form.add("clientId", clientId);
        form.add("categoryId", categoryId);
        MResponseContentListResult response =
                webResource.path("/showContents").queryParams(form)
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .header("X-TOKENID", tokenId)
                        .get(MResponseContentListResult.class);
        System.out.println(tokenId);
        //System.out.println("-------------------------------------------------------------------------------------");
        List<Content> contents = new ArrayList<>();
        List<MContentWall> content = response.getContents();
        for (MContentWall c : content) {
            try {
                ContentBuilder cb = new ContentBuilderMCW(c);
                ContentBuilderDirector director = new ContentBuilderDirector(cb);
                contents.add(director.build());
            } catch (InvalidContentException e) {
                System.err.println(e.getError());
            }
            /*System.out.println("getId: " + c.getId());
            System.out.println("getOwner: " + c.getOwner());
            System.out.println("getUserId: " + c.getUserId());
            System.out.println("getCreationDate: " + c.getCreationDate().toString());
            System.out.println("getContentType: " + c.getContentType().name());
            System.out.println("getDeliveryInfoSize: " + c.getDeliveryInfo().size());
            System.out.println("getLinkedCategories: " + c.getLinkedCategories().size());
            System.out.println("getImetadata: " + c.getImetadata().size());
            System.out.println("getLinkedContents: " + c.getLinkedContents().size());
            System.out.println("getMetadatasSize: " + c.getMetadatas().size());
            System.out.println("getLocalesSize: " + c.getLocales().size());
            for (MContent4Locale l : c.getLocales()) {
                System.out.println("   #" + l.getName());
                System.out.println("    " + l.getDescription());
                System.out.println("    " + l.getLocale());
            }
            System.out.println("getPrettyIdsSize: " + c.getPrettyIds().size());
            System.out.println("getPropertiesSize: " + c.getProperties().size());
            System.out.println("getProviders: " + c.getProviders().size());

            System.out.println("-------------------------------------------------------------------------------------");*/
        }
        model.addAttribute("contenuti", contents);
        return "Index";
    }

    @RequestMapping("/viewContent/{id}")
    public String showContent(@PathVariable String id, Model model) {
        String endPoint = "http://test-view.4me.it/api/xcontents/resources/content/";
        String clientId = "test";
        Object tokenId = session.getSession().getAttribute("tokenId");
        String contentId = id;
        Client client = Client.create();
        WebResource webResource = client.resource(endPoint);
        Form form = new Form();
        form.add("clientId",clientId);
        form.add("contentId",contentId);
        form.add("returnLinkedContents",true);
        form.add("returnLinkedCategories",true);
        form.add("returnThumbUrl",true);
        form.add("returnItags",true);
        form.add("returnImetadata",true);
        MResponseContentDetail response =
                webResource.path("/detail").queryParams(form)
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .header("X-TOKENID", tokenId).get(MResponseContentDetail.class);
        Content c = null;
        try{
            ContentBuilder cb = new ContentBuilderMRCD(response);
            ContentBuilderDirector director = new ContentBuilderDirector(cb);
            c = director.build();
        } catch (InvalidContentException e) {
            System.err.println(e.getError());
        }
        model.addAttribute("content",c);
        return "Show";
    }
}
