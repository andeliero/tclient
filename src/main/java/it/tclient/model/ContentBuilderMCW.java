package it.tclient.model;

import it.newvision.nvp.webtv.services.model.delivery.MContentWall;
import it.newvision.nvp.xcontents.model.MContent4Locale;

public class ContentBuilderMCW implements ContentBuilder {

    private MContentWall mContentWall;
    private Content content;

    public ContentBuilderMCW(MContentWall mcw) throws InvalidContentException{
        mContentWall = mcw;
        switch (mContentWall.getContentType().toString()){
            case "IMAGE":
                content = new ImageContent();
                break;
            case "PAGELET":
                content = new PageletContent();
                break;
            default:
                throw new InvalidContentException(mContentWall.getContentType().toString());
        }
    }

    @Override
    public void buildId() {
        content.setId(mContentWall.getId());
    }

    @Override
    public void buildThumb() {
        content.setThumb(mContentWall.getDynThumbService());
    }

    @Override
    public void buildTitle() {
        content.setTitle(mContentWall.getId());
    }

    @Override
    public void buildCreationDate() {
        content.setCreationDate(mContentWall.getCreationDate().toString());
    }

    @Override
    public void buildAuthor() {
        content.setAuthor(mContentWall.getUserId());
    }

    @Override
    public void buildDescription() {
        for (MContent4Locale l : mContentWall.getLocales()) {
            if (l.getLocale().equals("EN"))
                content.setDescription(l.getName());
        }
    }

    @Override
    public Content getResult() {
        return content;
    }
}
