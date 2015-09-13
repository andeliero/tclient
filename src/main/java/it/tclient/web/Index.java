package it.tclient.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@EnableAutoConfiguration
public class Index {

    @Autowired
    private HttpServletRequest session;

    @RequestMapping(value={"/","/index","/home"})
    public String index() {
        System.out.println(session.getSession().getAttribute("tokenId"));
        return "Index";
    }
}
