package com.etapps.trovenla.api;

import android.content.Context;

import com.etapps.trovenla.utils.Constants;

import retrofit.RestAdapter;

/**
 * Created by emanuele on 15/07/15.
 */
public class TroveRest {
    private static RestAdapter sharedInstance = null;

    public static <S> S getAdapter(Context mContext, Class<S> serviceClass) {
        if (sharedInstance == null) {

            sharedInstance = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setEndpoint(Constants.API_ENDPOINT)
                    .build();
        }

        return sharedInstance.create(serviceClass);
    }
}
