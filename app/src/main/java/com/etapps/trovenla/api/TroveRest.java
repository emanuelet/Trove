package com.etapps.trovenla.api;

import com.etapps.trovenla.models.queries.Work;
import com.etapps.trovenla.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emanuele on 15/07/15.
 */
public class TroveRest {
    private static Retrofit sharedInstance = null;

    public static <S> S getAdapter(Class<S> serviceClass) {
        if (sharedInstance == null) {
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Work.class, new Work.WorkDeserializer())
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            sharedInstance = new Retrofit.Builder()
                    .baseUrl(Constants.API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return sharedInstance.create(serviceClass);
    }
}
