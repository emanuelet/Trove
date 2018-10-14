
package com.etapps.trovenla.models.queries;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private List<String> contributor = null;
    @SerializedName("issued")
    @Expose
    private String issued;
    @SerializedName("type")
    @Expose
    private List<String> type = null;
    @SerializedName("holdingsCount")
    @Expose
    private long holdingsCount;
    @SerializedName("versionCount")
    @Expose
    private long versionCount;
    @SerializedName("relevance")
    @Expose
    private Relevance relevance;
    private String mSnippet;
    @SerializedName("holding")
    @Expose
    private List<Holding> holding = null;
    @SerializedName("identifier")
    @Expose
    private List<Identifier> identifier = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Work withId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Work withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTroveUrl() {
        return troveUrl;
    }

    public void setTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
    }

    public Work withTroveUrl(String troveUrl) {
        this.troveUrl = troveUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Work withTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getContributor() {
        return contributor;
    }

    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    public Work withContributor(List<String> contributor) {
        this.contributor = contributor;
        return this;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public Work withIssued(String issued) {
        this.issued = issued;
        return this;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Work withType(List<String> type) {
        this.type = type;
        return this;
    }

    public long getHoldingsCount() {
        return holdingsCount;
    }

    public void setHoldingsCount(long holdingsCount) {
        this.holdingsCount = holdingsCount;
    }

    public Work withHoldingsCount(long holdingsCount) {
        this.holdingsCount = holdingsCount;
        return this;
    }

    public long getVersionCount() {
        return versionCount;
    }

    public void setVersionCount(long versionCount) {
        this.versionCount = versionCount;
    }

    public Work withVersionCount(long versionCount) {
        this.versionCount = versionCount;
        return this;
    }

    public Relevance getRelevance() {
        return relevance;
    }

    public void setRelevance(Relevance relevance) {
        this.relevance = relevance;
    }

    public Work withRelevance(Relevance relevance) {
        this.relevance = relevance;
        return this;
    }

    public String getSnippet() {
        return mSnippet;
    }

    public void setSnippet(String snippet) {
        this.mSnippet = snippet;
    }

    public Work withSnippet(String snippet) {
        this.mSnippet = snippet;
        return this;
    }

    public List<Holding> getHolding() {
        return holding;
    }

    public void setHolding(List<Holding> holding) {
        this.holding = holding;
    }

    public Work withHolding(List<Holding> holding) {
        this.holding = holding;
        return this;
    }

    public List<Identifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
    }

    public Work withIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
        return this;
    }

    public static class WorkDeserializer implements JsonDeserializer<Work> {

        @Override
        public Work deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Work accountState = new Gson().fromJson(json, Work.class);
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("snippet")) {
                JsonElement elem = jsonObject.get("snippet");
                if (elem != null && !elem.isJsonNull()) {
                    if(elem.isJsonArray()){
                        Iterator<JsonElement> it = elem.getAsJsonArray().iterator();
                        StringBuilder tmp = new StringBuilder();
                        while(it.hasNext()){
                            tmp.append(it.next().getAsString()).append("<br/>");
                        }
                        accountState.setSnippet(tmp.toString());
                    }else{
                        accountState.setSnippet(elem.getAsString());
                    }
                }
            }
            return accountState ;
        }
    }

}
