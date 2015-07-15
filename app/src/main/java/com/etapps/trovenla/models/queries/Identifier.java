
package com.etapps.trovenla.models.queries;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Identifier {

    @Expose
    private String type;
    @Expose
    private String linktype;
    @Expose
    private String linktext;
    @Expose
    private String value;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The linktype
     */
    public String getLinktype() {
        return linktype;
    }

    /**
     * 
     * @param linktype
     *     The linktype
     */
    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }

    /**
     * 
     * @return
     *     The linktext
     */
    public String getLinktext() {
        return linktext;
    }

    /**
     * 
     * @param linktext
     *     The linktext
     */
    public void setLinktext(String linktext) {
        this.linktext = linktext;
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
