package it.tclient.model;

import it.newvision.nvp.xcontents.model.MContent4Locale;
import it.newvision.nvp.xcontents.services.model.content.MResponseContentDetail;

public class ContentBuilderMRCD implements ContentBuilder {

    private MResponseContentDetail mResponseContentDetail;
    private Content content;

    public ContentBuilderMRCD(MResponseContentDetail mrcd) throws InvalidContentException {
        mResponseContentDetail = mrcd;
        switch (mResponseContentDetail.content().getContentType().toString()){
            case "IMAGE":
                content = new ImageContent();
                break;
            case "PAGELET":
                content = new PageletContent();
                break;
            default:
                throw new InvalidContentException(mResponseContentDetail.getContent().getContentType().toString());
        }
    }

    @Override
    public void buildId() {
        content.setId(mResponseContentDetail.getContent().getId());
    }

    @Override
    public void buildThumb() {
        for (String thumb : mResponseContentDetail.getThumbUrls()) {
            if (content.getThumb() == null) {
                content.setThumb(thumb);
            }
        }
    }

    @Override
    public void buildTitle() {
        content.setTitle(mResponseContentDetail.getContent().getId());
    }

    @Override
    public void buildCreationDate() {
        content.setCreationDate(mResponseContentDetail.getContent().getCreationDate().toString());
    }

    @Override
    public void buildAuthor() {
        content.setAuthor(mResponseContentDetail.content().getUserId());
    }

    @Override
    public void buildDescription() {
        for (MContent4Locale l : mResponseContentDetail.getContent().getLocales()) {
            if (l.getLocale().equals("EN"))
                content.setDescription(l.getName());
        }
    }

    @Override
    public Content getResult() {
        return content;
    }
}
