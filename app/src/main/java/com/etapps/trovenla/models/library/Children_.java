
package com.etapps.trovenla.models.library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Children_ {

    @SerializedName("contributor")
    @Expose
    private List<Contributor__> contributor = null;

    public List<Contributor__> getContributor() {
        return contributor;
    }

    public void setContributor(List<Contributor__> contributor) {
        this.contributor = contributor;
    }

    public Children_ withContributor(List<Contributor__> contributor) {
        this.contributor = contributor;
        return this;
    }

}
