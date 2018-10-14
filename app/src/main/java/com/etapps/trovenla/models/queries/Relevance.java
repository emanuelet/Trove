
package com.etapps.trovenla.models.queries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relevance {

    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("value")
    @Expose
    private String value;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Relevance withScore(String score) {
        this.score = score;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Relevance withValue(String value) {
        this.value = value;
        return this;
    }

}
