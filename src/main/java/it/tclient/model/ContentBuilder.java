package it.tclient.model;

public interface ContentBuilder {
    void buildId();
    void buildThumb();
    void buildTitle();
    void buildCreationDate();
    void buildAuthor();
    void buildDescription();
    Content getResult();
}
