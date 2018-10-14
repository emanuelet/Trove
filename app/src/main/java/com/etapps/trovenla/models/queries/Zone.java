
package com.etapps.trovenla.models.queries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zone {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("records")
    @Expose
    private Records records;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Zone withName(String name) {
        this.name = name;
        return this;
    }

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

    public Zone withRecords(Records records) {
        this.records = records;
        return this;
    }

}
