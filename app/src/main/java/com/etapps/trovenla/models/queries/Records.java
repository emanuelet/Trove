
package com.etapps.trovenla.models.queries;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("nextStart")
    @Expose
    private String nextStart;
    @SerializedName("work")
    @Expose
    private List<Work> work = null;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Records withS(String s) {
        this.s = s;
        return this;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Records withN(String n) {
        this.n = n;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Records withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Records withNext(String next) {
        this.next = next;
        return this;
    }

    public String getNextStart() {
        return nextStart;
    }

    public void setNextStart(String nextStart) {
        this.nextStart = nextStart;
    }

    public Records withNextStart(String nextStart) {
        this.nextStart = nextStart;
        return this;
    }

    public List<Work> getWork() {
        return work;
    }

    public void setWork(List<Work> work) {
        this.work = work;
    }

    public Records withWork(List<Work> work) {
        this.work = work;
        return this;
    }

}
