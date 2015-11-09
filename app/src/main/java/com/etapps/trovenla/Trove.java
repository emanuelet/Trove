package com.etapps.trovenla;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.etapps.trovenla.utils.ConfigUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by emanuele on 12/04/15.
 */
public class Trove extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(ConfigUtils.getUrl("TWITTER_KEY", this), ConfigUtils.getUrl("TWITTER_SECRET", this));
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));

        if (BuildConfig.DEBUG) {

        }
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Realm realm = Realm.getDefaultInstance();
        realm.close();
    }
}
