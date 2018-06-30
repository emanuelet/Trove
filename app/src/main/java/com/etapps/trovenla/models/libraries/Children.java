
package com.etapps.trovenla.models.libraries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Children {

    @Expose
    private List<Contributor> contributor = new ArrayList<Contributor>();

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
