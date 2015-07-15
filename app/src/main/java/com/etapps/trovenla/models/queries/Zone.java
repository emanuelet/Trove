
package com.etapps.trovenla.models.queries;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Zone {

    @Expose
    private String name;
    @Expose
    private Records records;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The records
     */
    public Records getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     *     The records
     */
    public void setRecords(Records records) {
        this.records = records;
    }

}
