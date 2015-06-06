package com.etapps.trovenla;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.etapps.trovenla.utils.ConfigUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by emanuele on 12/04/15.
 */
public class Trove extends Application {

    @Override
    public void onCreate() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(ConfigUtils.getUrl("TWITTER_KEY", this), ConfigUtils.getUrl("TWITTER_SECRET", this));
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    }
}
