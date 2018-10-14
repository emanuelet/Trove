
package com.etapps.trovenla.models.queries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Identifier {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("linktype")
    @Expose
    private String linktype;
    @SerializedName("linktext")
    @Expose
    private String linktext;
    @SerializedName("value")
    @Expose
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Identifier withType(String type) {
        this.type = type;
        return this;
    }

    public String getLinktype() {
        return linktype;
    }

    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }

    public Identifier withLinktype(String linktype) {
        this.linktype = linktype;
        return this;
    }

    public String getLinktext() {
        return linktext;
    }

    public void setLinktext(String linktext) {
        this.linktext = linktext;
    }

    public Identifier withLinktext(String linktext) {
        this.linktext = linktext;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Identifier withValue(String value) {
        this.value = value;
        return this;
    }

}
