
package com.etapps.trovenla.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Response {

    @Expose
    private String total;
    @Expose
    private List<Contributor> contributor = new ArrayList<Contributor>();

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The contributor
     */
    public List<Contributor> getContributor() {
        return contributor;
    }

    /**
     * 
     * @param contributor
     *     The contributor
     */
    public void setContributor(List<Contributor> contributor) {
        this.contributor = contributor;
    }

}
