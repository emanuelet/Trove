
package com.etapps.trovenla.models.queries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallNumber {

    @SerializedName("localIdentifier")
    @Expose
    private String localIdentifier;
    @SerializedName("value")
    @Expose
    private String value;

    public String getLocalIdentifier() {
        return localIdentifier;
    }

    public void setLocalIdentifier(String localIdentifier) {
        this.localIdentifier = localIdentifier;
    }

    public CallNumber withLocalIdentifier(String localIdentifier) {
        this.localIdentifier = localIdentifier;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CallNumber withValue(String value) {
        this.value = value;
        return this;
    }

}
