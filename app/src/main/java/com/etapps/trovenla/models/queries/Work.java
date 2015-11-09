
package com.etapps.trovenla.models.queries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Work {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("troveUrl")
    @Expose
    private String troveUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("contributor")
    @Expose
    private List<String> contributor = new ArrayList<String>();
    @SerializedName("issued")
    @Expose
    private String issued;
    @SerializedName("type")
    @Expose
    private List<String> type = new ArrayList<String>();
    @SerializedName("holdingsCount")
    @Expose
    private long holdingsCount;
    @SerializedName("versionCount")
    @Expose
    private long versionCount;
    @SerializedName("relevance")
    @Expose
    private Relevance relevance;
    @SerializedName("holding")
    @Expose
    private List<Holding> holding = new ArrayList<Holding>();
    @SerializedName("identifier")
    @Expose
    private List<Identifier> identifier = new ArrayList<Identifier>();
    @SerializedName("snippet")
    @Expose
    private String snippet;

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
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The contributor
     */
    public List<String> getContributor() {
        return contributor;
    }

    /**
     * 
     * @param contributor
     *     The contributor
     */
    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    /**
     * 
     * @return
     *     The issued
     */
    public String getIssued() {
        return issued;
    }

    /**
     * 
     * @param issued
     *     The issued
     */
    public void setIssued(String issued) {
        this.issued = issued;
    }

    /**
     * 
     * @return
     *     The type
     */
    public List<String> getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(List<String> type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The holdingsCount
     */
    public long getHoldingsCount() {
        return holdingsCount;
    }

    /**
     * 
     * @param holdingsCount
     *     The holdingsCount
     */
    public void setHoldingsCount(long holdingsCount) {
        this.holdingsCount = holdingsCount;
    }

    /**
     * 
     * @return
     *     The versionCount
     */
    public long getVersionCount() {
        return versionCount;
    }

    /**
     * 
     * @param versionCount
     *     The versionCount
     */
    public void setVersionCount(long versionCount) {
        this.versionCount = versionCount;
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
     *     The holding
     */
    public List<Holding> getHolding() {
        return holding;
    }

    /**
     * 
     * @param holding
     *     The holding
     */
    public void setHolding(List<Holding> holding) {
        this.holding = holding;
    }

    /**
     * 
     * @return
     *     The identifier
     */
    public List<Identifier> getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier
     *     The identifier
     */
    public void setIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
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

}
