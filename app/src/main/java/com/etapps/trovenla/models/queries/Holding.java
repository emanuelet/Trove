
package com.etapps.trovenla.models.queries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Holding {

    @Expose
    private String nuc;
    @Expose
    private List<String> contributor = new ArrayList<String>();
    @Expose
    private Url url;
    @Expose
    private List<String> callNumber = new ArrayList<String>();

    /**
     * 
     * @return
     *     The nuc
     */
    public String getNuc() {
        return nuc;
    }

    /**
     * 
     * @param nuc
     *     The nuc
     */
    public void setNuc(String nuc) {
        this.nuc = nuc;
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
     *     The url
     */
    public Url getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(Url url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The callNumber
     */
    public List<String> getCallNumber() {
        return callNumber;
    }

    /**
     * 
     * @param callNumber
     *     The callNumber
     */
    public void setCallNumber(List<String> callNumber) {
        this.callNumber = callNumber;
    }

}
