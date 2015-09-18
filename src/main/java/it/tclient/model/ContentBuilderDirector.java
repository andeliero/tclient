package it.tclient.model;


public class ContentBuilderDirector {

    ContentBuilder builder;

    public ContentBuilderDirector(ContentBuilder cb) {
        builder = cb;
    }

    public Content build() {
        //potrei guardare il tipo di contenuto qui col switch case
        builder.buildId();
        builder.buildTitle();
        builder.buildCreationDate();
        builder.buildThumb();
        builder.buildAuthor();
        builder.buildDescription();
        return builder.getResult();
    }

}
