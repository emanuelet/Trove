
package com.etapps.trovenla.models.ol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableOfContent {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("pagenum")
    @Expose
    private String pagenum;
    @SerializedName("level")
    @Expose
    private long level;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TableOfContent withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TableOfContent withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public TableOfContent withPagenum(String pagenum) {
        this.pagenum = pagenum;
        return this;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public TableOfContent withLevel(long level) {
        this.level = level;
        return this;
    }

}
