package com.etapps.trovenla.api;

import android.content.Context;

import com.etapps.trovenla.models.queries.CallNumber;
import com.etapps.trovenla.models.queries.CallNumberString;
import com.etapps.trovenla.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by emanuele on 15/07/15.
 */
public class TroveRest {

    private static RestAdapter sharedInstance = null;

    public static <S> S getAdapter(Context mContext, Class<S> serviceClass) {
        if (sharedInstance == null) {

            Type myOtherClassListType = new TypeToken<List<CallNumber>>() {
            }.getType();



            sharedInstance = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setEndpoint(Constants.API_ENDPOINT)
                    .build();
        }

        return sharedInstance.create(serviceClass);
    }
}
