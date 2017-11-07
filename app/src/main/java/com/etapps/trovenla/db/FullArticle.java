package com.etapps.trovenla.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emanuele on 04/11/17.
 */

public class FullArticle extends RealmObject {

    @PrimaryKey
    private String id;
    private String url;
    private String heading;
    private String category;
    private String title;
    private String date;
    private long page;
    private long pageSequence;
    private String troveUrl;
    private String illustrated;
    private long wordCount;
    private long correctionCount;
    private long listCount;
    private long tagCount;
    private long commentCount;
    private String identifier;
    private String trovePageUrl;
    private String pdf;
    private String articleText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageSequence() {
        return pageSequence;
    }

    public void setPageSequence(long pageSequence) {
        this.pageSequence = pageSequence;
    }

    public String getTroveUrl() {
        return troveUrl;
    }

    public void setTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
    }

    public String getIllustrated() {
        return illustrated;
    }

    public void setIllustrated(String illustrated) {
        this.illustrated = illustrated;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public long getCorrectionCount() {
        return correctionCount;
    }

    public void setCorrectionCount(long correctionCount) {
        this.correctionCount = correctionCount;
    }

    public long getListCount() {
        return listCount;
    }

    public void setListCount(long listCount) {
        this.listCount = listCount;
    }

    public long getTagCount() {
        return tagCount;
    }

    public void setTagCount(long tagCount) {
        this.tagCount = tagCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTrovePageUrl() {
        return trovePageUrl;
    }

    public void setTrovePageUrl(String trovePageUrl) {
        this.trovePageUrl = trovePageUrl;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
}
