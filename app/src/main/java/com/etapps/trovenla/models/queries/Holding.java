
package com.etapps.trovenla.models.queries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Holding {

    @SerializedName("nuc")
    @Expose
    private String nuc;
    @SerializedName("contributor")
    @Expose
    private List<String> contributor = new ArrayList<String>();
//    @SerializedName("callNumber")
//    @Expose
//    private List<CallNumber> callNumber = new ArrayList<CallNumber>();
    @SerializedName("url")
    @Expose
    private Url url;

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
     *     The callNumber
     */
//    public List<CallNumber> getCallNumber() {
//        return callNumber;
//    }
//
//    /**
//     *
//     * @param callNumber
//     *     The callNumber
//     */
//    public void setCallNumber(List<CallNumber> callNumber) {
//        this.callNumber = callNumber;
//    }

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

}
