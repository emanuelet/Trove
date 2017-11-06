
package com.etapps.trovenla.models.library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contributor__ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nuc")
    @Expose
    private List<String> nuc = null;
    @SerializedName("totalholdings")
    @Expose
    private long totalholdings;
    @SerializedName("accesspolicy")
    @Expose
    private String accesspolicy;
    @SerializedName("algentry")
    @Expose
    private String algentry;
    @SerializedName("shortname")
    @Expose
    private String shortname;
    @SerializedName("homepage")
    @Expose
    private String homepage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contributor__ withId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contributor__ withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contributor__ withName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getNuc() {
        return nuc;
    }

    public void setNuc(List<String> nuc) {
        this.nuc = nuc;
    }

    public Contributor__ withNuc(List<String> nuc) {
        this.nuc = nuc;
        return this;
    }

    public long getTotalholdings() {
        return totalholdings;
    }

    public void setTotalholdings(long totalholdings) {
        this.totalholdings = totalholdings;
    }

    public Contributor__ withTotalholdings(long totalholdings) {
        this.totalholdings = totalholdings;
        return this;
    }

    public String getAccesspolicy() {
        return accesspolicy;
    }

    public void setAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
    }

    public Contributor__ withAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
        return this;
    }

    public String getAlgentry() {
        return algentry;
    }

    public void setAlgentry(String algentry) {
        this.algentry = algentry;
    }

    public Contributor__ withAlgentry(String algentry) {
        this.algentry = algentry;
        return this;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Contributor__ withShortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Contributor__ withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

}
