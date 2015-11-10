
package com.etapps.trovenla.models.newspapers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Records {

    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("n")
    @Expose
    private String n;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("article")
    @Expose
    private List<Article> article = new ArrayList<Article>();

    /**
     * 
     * @return
     *     The s
     */
    public String getS() {
        return s;
    }

    /**
     * 
     * @param s
     *     The s
     */
    public void setS(String s) {
        this.s = s;
    }

    /**
     * 
     * @return
     *     The n
     */
    public String getN() {
        return n;
    }

    /**
     * 
     * @param n
     *     The n
     */
    public void setN(String n) {
        this.n = n;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * 
     * @return
     *     The article
     */
    public List<Article> getArticle() {
        return article;
    }

    /**
     * 
     * @param article
     *     The article
     */
    public void setArticle(List<Article> article) {
        this.article = article;
    }

}
