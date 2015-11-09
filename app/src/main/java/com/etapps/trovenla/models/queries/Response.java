
package com.etapps.trovenla.models.queries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Response {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("zone")
    @Expose
    private List<Zone> zone = new ArrayList<Zone>();

    /**
     * 
     * @return
     *     The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 
     * @return
     *     The zone
     */
    public List<Zone> getZone() {
        return zone;
    }

    /**
     * 
     * @param zone
     *     The zone
     */
    public void setZone(List<Zone> zone) {
        this.zone = zone;
    }

}
