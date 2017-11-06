package com.etapps.trovenla.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emanuele on 04/11/17.
 */

public class ArticleDb extends RealmObject {

    @PrimaryKey
    private String id;
    private String url;
    private String heading;
    private String category;
    private String title;
    private String date;
    private long page;
    private long pageSequence;
    private String score;
    private String value;
    private String snippet;
    private String troveUrl;
    private String edition;

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getTroveUrl() {
        return troveUrl;
    }

    public void setTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getPageSequence() {
        return pageSequence;
    }

    public void setPageSequence(long pageSequence) {
        this.pageSequence = pageSequence;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
