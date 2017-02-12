package com.etapps.trovenla.api;

import com.etapps.trovenla.utils.Constants;

import retrofit2.Retrofit;

/**
 * Created by emanuele on 15/07/15.
 */
public class TroveRest2 {
    private static Retrofit sharedInstance = null;

    public static <S> S getAdapter(Class<S> serviceClass) {
        if (sharedInstance == null) {

            sharedInstance = new Retrofit.Builder()
                    .baseUrl(Constants.API_ENDPOINT)
                    .build();
        }

        return sharedInstance.create(serviceClass);
    }
}
