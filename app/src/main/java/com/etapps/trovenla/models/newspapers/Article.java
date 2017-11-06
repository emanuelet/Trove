
package com.etapps.trovenla.models.newspapers;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Article {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("relevance")
    @Expose
    private Relevance relevance;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("troveUrl")
    @Expose
    private String troveUrl;
    @SerializedName("edition")
    @Expose
    private String edition;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * 
     * @param heading
     *     The heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The page
     */
    public long getPage() {
        return page;
    }

    /**
     * 
     * @param page
     *     The page
     */
    public void setPage(long page) {
        this.page = page;
    }


    /**
     * 
     * @return
     *     The relevance
     */
    public Relevance getRelevance() {
        return relevance;
    }

    /**
     * 
     * @param relevance
     *     The relevance
     */
    public void setRelevance(Relevance relevance) {
        this.relevance = relevance;
    }

    /**
     * 
     * @return
     *     The snippet
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * 
     * @param snippet
     *     The snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * 
     * @return
     *     The troveUrl
     */
    public String getTroveUrl() {
        return troveUrl;
    }

    /**
     * 
     * @param troveUrl
     *     The troveUrl
     */
    public void setTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
    }

    /**
     * 
     * @return
     *     The edition
     */
    public String getEdition() {
        return edition;
    }

    /**
     * 
     * @param edition
     *     The edition
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

}
