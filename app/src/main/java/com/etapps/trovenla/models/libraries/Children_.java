
package com.etapps.trovenla.models.libraries;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Children_ {

    @Expose
    private List<Contributor__> contributor = new ArrayList<Contributor__>();

    /**
     * 
     * @return
     *     The contributor
     */
    public List<Contributor__> getContributor() {
        return contributor;
    }

    /**
     * 
     * @param contributor
     *     The contributor
     */
    public void setContributor(List<Contributor__> contributor) {
        this.contributor = contributor;
    }

}
