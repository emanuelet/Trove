
package com.etapps.trovenla.models.ol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ebook {

    @SerializedName("checkedout")
    @Expose
    private boolean checkedout;
    @SerializedName("formats")
    @Expose
    private Formats formats;
    @SerializedName("preview_url")
    @Expose
    private String previewUrl;
    @SerializedName("borrow_url")
    @Expose
    private String borrowUrl;
    @SerializedName("availability")
    @Expose
    private String availability;

    public boolean isCheckedout() {
        return checkedout;
    }

    public void setCheckedout(boolean checkedout) {
        this.checkedout = checkedout;
    }

    public Ebook withCheckedout(boolean checkedout) {
        this.checkedout = checkedout;
        return this;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Ebook withFormats(Formats formats) {
        this.formats = formats;
        return this;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Ebook withPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public Ebook withBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
        return this;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Ebook withAvailability(String availability) {
        this.availability = availability;
        return this;
    }

}
