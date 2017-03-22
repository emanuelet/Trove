
package com.etapps.trovenla.models.ol;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classifications {

    @SerializedName("dewey_decimal_class")
    @Expose
    private List<String> deweyDecimalClass = null;
    @SerializedName("lc_classifications")
    @Expose
    private List<String> lcClassifications = null;

    public List<String> getDeweyDecimalClass() {
        return deweyDecimalClass;
    }

    public void setDeweyDecimalClass(List<String> deweyDecimalClass) {
        this.deweyDecimalClass = deweyDecimalClass;
    }

    public Classifications withDeweyDecimalClass(List<String> deweyDecimalClass) {
        this.deweyDecimalClass = deweyDecimalClass;
        return this;
    }

    public List<String> getLcClassifications() {
        return lcClassifications;
    }

    public void setLcClassifications(List<String> lcClassifications) {
        this.lcClassifications = lcClassifications;
    }

    public Classifications withLcClassifications(List<String> lcClassifications) {
        this.lcClassifications = lcClassifications;
        return this;
    }

}
