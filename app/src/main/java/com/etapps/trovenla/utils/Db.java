package com.etapps.trovenla.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by emanuele on 15/07/15.
 */
public class Db {
    public static Realm getRealm(Context mContext) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm.deleteRealm(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();

        // Create a new empty instance of Realm
        return realm;
    }
}
