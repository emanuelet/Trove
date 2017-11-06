
package com.etapps.trovenla.models.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Library {

    @SerializedName("contributor")
    @Expose
    private Contributor contributor;

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public Library withContributor(Contributor contributor) {
        this.contributor = contributor;
        return this;
    }

}
