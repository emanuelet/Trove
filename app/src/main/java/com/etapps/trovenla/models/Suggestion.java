package com.etapps.trovenla.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ian Ryan on 11/10/2015.
 */
public class Suggestion extends RealmObject {

    @PrimaryKey
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
