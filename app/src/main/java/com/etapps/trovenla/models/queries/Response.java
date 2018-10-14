
package com.etapps.trovenla.models.queries;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("zone")
    @Expose
    private List<Zone> zone = null;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Response withQuery(String query) {
        this.query = query;
        return this;
    }

    public List<Zone> getZone() {
        return zone;
    }

    public void setZone(List<Zone> zone) {
        this.zone = zone;
    }

    public Response withZone(List<Zone> zone) {
        this.zone = zone;
        return this;
    }

}
