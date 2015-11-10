
package com.etapps.trovenla.models.articles;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FullArticle {

    @SerializedName("article")
    @Expose
    private Article article;

    /**
     * 
     * @return
     *     The article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * 
     * @param article
     *     The article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

}
