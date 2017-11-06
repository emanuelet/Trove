
package com.etapps.trovenla.models.library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contributor_ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("totalholdings")
    @Expose
    private long totalholdings;
    @SerializedName("children")
    @Expose
    private Children_ children;
    @SerializedName("nuc")
    @Expose
    private List<String> nuc = null;
    @SerializedName("shortname")
    @Expose
    private String shortname;
    @SerializedName("accesspolicy")
    @Expose
    private String accesspolicy;
    @SerializedName("algentry")
    @Expose
    private String algentry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contributor_ withId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contributor_ withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contributor_ withName(String name) {
        this.name = name;
        return this;
    }

    public long getTotalholdings() {
        return totalholdings;
    }

    public void setTotalholdings(long totalholdings) {
        this.totalholdings = totalholdings;
    }

    public Contributor_ withTotalholdings(long totalholdings) {
        this.totalholdings = totalholdings;
        return this;
    }

    public Children_ getChildren() {
        return children;
    }

    public void setChildren(Children_ children) {
        this.children = children;
    }

    public Contributor_ withChildren(Children_ children) {
        this.children = children;
        return this;
    }

    public List<String> getNuc() {
        return nuc;
    }

    public void setNuc(List<String> nuc) {
        this.nuc = nuc;
    }

    public Contributor_ withNuc(List<String> nuc) {
        this.nuc = nuc;
        return this;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Contributor_ withShortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public String getAccesspolicy() {
        return accesspolicy;
    }

    public void setAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
    }

    public Contributor_ withAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
        return this;
    }

    public String getAlgentry() {
        return algentry;
    }

    public void setAlgentry(String algentry) {
        this.algentry = algentry;
    }

    public Contributor_ withAlgentry(String algentry) {
        this.algentry = algentry;
        return this;
    }

}
