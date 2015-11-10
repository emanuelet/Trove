
package com.etapps.trovenla.models.queries;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CallNumber {

    @SerializedName("localIdentifier")
    @Expose
    private String localIdentifier;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * 
     * @return
     *     The localIdentifier
     */
    public String getLocalIdentifier() {
        return localIdentifier;
    }

    /**
     * 
     * @param localIdentifier
     *     The localIdentifier
     */
    public void setLocalIdentifier(String localIdentifier) {
        this.localIdentifier = localIdentifier;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
