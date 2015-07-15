
package com.etapps.trovenla.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Contributor_ {

    @Expose
    private String id;
    @Expose
    private String url;
    @Expose
    private String name;
    @Expose
    private List<String> nuc = new ArrayList<String>();
    @Expose
    private String shortname;
    @Expose
    private Long totalholdings;
    @Expose
    private String accesspolicy;
    @Expose
    private String homepage;
    @Expose
    private String algentry;
    @Expose
    private Children_ children;

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The nuc
     */
    public List<String> getNuc() {
        return nuc;
    }

    /**
     * 
     * @param nuc
     *     The nuc
     */
    public void setNuc(List<String> nuc) {
        this.nuc = nuc;
    }

    /**
     * 
     * @return
     *     The shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * 
     * @param shortname
     *     The shortname
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    /**
     * 
     * @return
     *     The totalholdings
     */
    public Long getTotalholdings() {
        return totalholdings;
    }

    /**
     * 
     * @param totalholdings
     *     The totalholdings
     */
    public void setTotalholdings(Long totalholdings) {
        this.totalholdings = totalholdings;
    }

    /**
     * 
     * @return
     *     The accesspolicy
     */
    public String getAccesspolicy() {
        return accesspolicy;
    }

    /**
     * 
     * @param accesspolicy
     *     The accesspolicy
     */
    public void setAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
    }

    /**
     * 
     * @return
     *     The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * 
     * @param homepage
     *     The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * 
     * @return
     *     The algentry
     */
    public String getAlgentry() {
        return algentry;
    }

    /**
     * 
     * @param algentry
     *     The algentry
     */
    public void setAlgentry(String algentry) {
        this.algentry = algentry;
    }

    /**
     * 
     * @return
     *     The children
     */
    public Children_ getChildren() {
        return children;
    }

    /**
     * 
     * @param children
     *     The children
     */
    public void setChildren(Children_ children) {
        this.children = children;
    }

}
