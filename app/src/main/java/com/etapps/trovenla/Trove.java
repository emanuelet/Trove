package com.etapps.trovenla;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.etapps.trovenla.utils.ConfigUtils;
import com.etapps.trovenla.utils.TroveJobCreator;
import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by emanuele on 12/04/15.
 */
public class Trove extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setMinimumSessionDuration(3000);
        mFirebaseAnalytics.setSessionTimeoutDuration(600000);

        Realm.setDefaultConfiguration(realmConfiguration);

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }

        JobManager.create(this).addJobCreator(new TroveJobCreator());
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Realm.getDefaultInstance().close();
    }
}
