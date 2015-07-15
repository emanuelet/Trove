
package com.etapps.trovenla.models.queries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Response {

    @Expose
    private String query;
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
