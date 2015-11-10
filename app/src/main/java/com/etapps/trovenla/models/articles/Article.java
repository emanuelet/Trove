
package com.etapps.trovenla.models.articles;

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
    @SerializedName("pageSequence")
    @Expose
    private long pageSequence;
    @SerializedName("troveUrl")
    @Expose
    private String troveUrl;
    @SerializedName("illustrated")
    @Expose
    private String illustrated;
    @SerializedName("wordCount")
    @Expose
    private long wordCount;
    @SerializedName("correctionCount")
    @Expose
    private long correctionCount;
    @SerializedName("listCount")
    @Expose
    private long listCount;
    @SerializedName("tagCount")
    @Expose
    private long tagCount;
    @SerializedName("commentCount")
    @Expose
    private long commentCount;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("trovePageUrl")
    @Expose
    private String trovePageUrl;
    @SerializedName("pdf")
    @Expose
    private String pdf;
    @SerializedName("articleText")
    @Expose
    private String articleText;

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
     *     The pageSequence
     */
    public long getPageSequence() {
        return pageSequence;
    }

    /**
     * 
     * @param pageSequence
     *     The pageSequence
     */
    public void setPageSequence(long pageSequence) {
        this.pageSequence = pageSequence;
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
     *     The illustrated
     */
    public String getIllustrated() {
        return illustrated;
    }

    /**
     * 
     * @param illustrated
     *     The illustrated
     */
    public void setIllustrated(String illustrated) {
        this.illustrated = illustrated;
    }

    /**
     * 
     * @return
     *     The wordCount
     */
    public long getWordCount() {
        return wordCount;
    }

    /**
     * 
     * @param wordCount
     *     The wordCount
     */
    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * 
     * @return
     *     The correctionCount
     */
    public long getCorrectionCount() {
        return correctionCount;
    }

    /**
     * 
     * @param correctionCount
     *     The correctionCount
     */
    public void setCorrectionCount(long correctionCount) {
        this.correctionCount = correctionCount;
    }

    /**
     * 
     * @return
     *     The listCount
     */
    public long getListCount() {
        return listCount;
    }

    /**
     * 
     * @param listCount
     *     The listCount
     */
    public void setListCount(long listCount) {
        this.listCount = listCount;
    }

    /**
     * 
     * @return
     *     The tagCount
     */
    public long getTagCount() {
        return tagCount;
    }

    /**
     * 
     * @param tagCount
     *     The tagCount
     */
    public void setTagCount(long tagCount) {
        this.tagCount = tagCount;
    }

    /**
     * 
     * @return
     *     The commentCount
     */
    public long getCommentCount() {
        return commentCount;
    }

    /**
     * 
     * @param commentCount
     *     The commentCount
     */
    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * 
     * @return
     *     The identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier
     *     The identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 
     * @return
     *     The trovePageUrl
     */
    public String getTrovePageUrl() {
        return trovePageUrl;
    }

    /**
     * 
     * @param trovePageUrl
     *     The trovePageUrl
     */
    public void setTrovePageUrl(String trovePageUrl) {
        this.trovePageUrl = trovePageUrl;
    }

    /**
     * 
     * @return
     *     The pdf
     */
    public String getPdf() {
        return pdf;
    }

    /**
     * 
     * @param pdf
     *     The pdf
     */
    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    /**
     * 
     * @return
     *     The articleText
     */
    public String getArticleText() {
        return articleText;
    }

    /**
     * 
     * @param articleText
     *     The articleText
     */
    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

}
