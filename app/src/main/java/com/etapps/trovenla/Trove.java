package com.etapps.trovenla;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.mopub.common.MoPub;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by emanuele on 12/04/15.
 */
public class Trove extends Application {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY =  ConfigUtils.getUrl("TWITTER_KEY");
    private static final String TWITTER_SECRET =  ConfigUtils.getUrl("TWITTER_SECRET");

    @Override
    public void onCreate() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new MoPub(), new Twitter(authConfig));
    }
}
