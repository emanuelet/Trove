
package com.etapps.trovenla.models.library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Children {

    @SerializedName("contributor")
    @Expose
    private List<Contributor_> contributor = null;

    public List<Contributor_> getContributor() {
        return contributor;
    }

    public void setContributor(List<Contributor_> contributor) {
        this.contributor = contributor;
    }

    public Children withContributor(List<Contributor_> contributor) {
        this.contributor = contributor;
        return this;
    }

}
