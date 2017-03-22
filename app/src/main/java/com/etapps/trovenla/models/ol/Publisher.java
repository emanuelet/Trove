
package com.etapps.trovenla.models.ol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Publisher {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publisher withName(String name) {
        this.name = name;
        return this;
    }

}
