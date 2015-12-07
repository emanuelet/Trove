package com.etapps.trovenla.db;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emanuele on 15/07/15.
 */
public class Book extends RealmObject {

    @PrimaryKey
    private String id;
    private String url;
    private String troveUrl;
    private String title;
    private String contributor;
    private String issued;
    private long holdingsCount;
    private long versionCount;
    private String score;
    private String value;
    private String snippet;
    private RealmList<Library> libraries;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The troveUrl
     */
    public String getTroveUrl() {
        return troveUrl;
    }

    /**
     * @param troveUrl The troveUrl
     */
    public void setTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The contributor
     */
    public String getContributor() {
        return contributor;
    }

    /**
     * @param contributor The contributor
     */
    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    /**
     * @return The issued
     */
    public String getIssued() {
        return issued;
    }

    /**
     * @param issued The issued
     */
    public void setIssued(String issued) {
        this.issued = issued;
    }


    public long getHoldingsCount() {
        return holdingsCount;
    }

    /**
     * @param holdingsCount The holdingsCount
     */
    public void setHoldingsCount(long holdingsCount) {
        this.holdingsCount = holdingsCount;
    }

    /**
     * @return The versionCount
     */
    public long getVersionCount() {
        return versionCount;
    }

    /**
     * @param versionCount The versionCount
     */
    public void setVersionCount(long versionCount) {
        this.versionCount = versionCount;
    }

    /**
     * @return The snippet
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * @param snippet The snippet
     */
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

    public RealmList<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(RealmList<Library> libraries) {
        this.libraries = libraries;
    }
}
