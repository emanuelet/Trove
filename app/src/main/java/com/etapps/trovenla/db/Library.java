package com.etapps.trovenla.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emanuele on 15/07/15.
 */
public class Library extends RealmObject {

    @PrimaryKey
    private String nuc;
    private String name;
    private String urlLib;
    private String urlHolding;
    private String shortname;
    private long totalholdings;
    private String accesspolicy;
    private String algentry;

    public String getUrlLib() {
        return urlLib;
    }

    public void setUrlLib(String urlLib) {
        this.urlLib = urlLib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNuc() {
        return nuc;
    }

    public void setNuc(String nuc) {
        this.nuc = nuc;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public long getTotalholdings() {
        return totalholdings;
    }

    public void setTotalholdings(long totalholdings) {
        this.totalholdings = totalholdings;
    }

    public String getAccesspolicy() {
        return accesspolicy;
    }

    public void setAccesspolicy(String accesspolicy) {
        this.accesspolicy = accesspolicy;
    }

    public String getAlgentry() {
        return algentry;
    }

    public void setAlgentry(String algentry) {
        this.algentry = algentry;
    }

    public String getUrlHolding() {
        return urlHolding;
    }

    public void setUrlHolding(String urlHolding) {
        this.urlHolding = urlHolding;
    }
}