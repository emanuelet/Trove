
package com.etapps.trovenla.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Children {

    @Expose
    private List<Contributor_> contributor = new ArrayList<Contributor_>();

    /**
     * 
     * @return
     *     The contributor
     */
    public List<Contributor_> getContributor() {
        return contributor;
    }

    /**
     * 
     * @param contributor
     *     The contributor
     */
    public void setContributor(List<Contributor_> contributor) {
        this.contributor = contributor;
    }

}
