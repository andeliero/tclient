package it.tclient.model;

import it.newvision.nvp.webtv.services.model.delivery.MContentWall;
import it.newvision.nvp.webtv.services.model.delivery.MDeliveryInfo;
import it.newvision.nvp.xcontents.model.MContent;
import it.newvision.nvp.xcontents.model.MContent4Locale;


public class Content {
    String Id;
    String title;
    String creationDate;
    String thumb;
    String author;
    String description;

    protected Content() { }

    public void setId(String i) { Id = i; }

    public void setTitle(String t) { title = t;}

    public void setCreationDate(String d) { creationDate = d; }

    public void setThumb(String t) { thumb = t; }

    public void setAuthor(String a) { author = a; }

    public void setDescription(String d) { description = d; }

    public String getId() { return Id; }

    public String getTitle() { return title; }

    public String getCreationDate() { return creationDate; }

    public String getThumb() { return thumb; }

    public String getAuthor() { return author; }

    public String getDescription() { return description; }

}
