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
}