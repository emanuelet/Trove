
package com.etapps.trovenla.models.queries;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Holding {

    @SerializedName("nuc")
    @Expose
    private String nuc;
    @SerializedName("contributor")
    @Expose
    private List<String> contributor = null;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("callNumber")
    @Expose
    private List<CallNumber> callNumber = null;

    public String getNuc() {
        return nuc;
    }

    public void setNuc(String nuc) {
        this.nuc = nuc;
    }

    public Holding withNuc(String nuc) {
        this.nuc = nuc;
        return this;
    }

    public List<String> getContributor() {
        return contributor;
    }

    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    public Holding withContributor(List<String> contributor) {
        this.contributor = contributor;
        return this;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Holding withUrl(Url url) {
        this.url = url;
        return this;
    }

    public List<CallNumber> getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(List<CallNumber> callNumber) {
        this.callNumber = callNumber;
    }

    public Holding withCallNumber(List<CallNumber> callNumber) {
        this.callNumber = callNumber;
        return this;
    }

}
