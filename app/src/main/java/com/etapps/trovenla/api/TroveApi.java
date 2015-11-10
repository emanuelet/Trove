package com.etapps.trovenla.api;

import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.articles.FullArticle;
import com.etapps.trovenla.models.newspapers.Newspaper;
import com.etapps.trovenla.models.queries.Books;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by emanuele on 15/07/15.
 */
public interface TroveApi {

//    QUERIES

    @GET("/contributor")
    void getLibraries(@Query("key") String key,
                      @Query("encoding") String format,
                      @Query("reclevel") String reclevel,
                      Callback<Libraries> callback);

    @GET("/result")
    void getContent(@Query("key") String key,
                    @Query("encoding") String format,
                    @Query("n") String results,
                    @Query("q") String query,
                    @Query("zone") String zone,
                    @Query("include") String include,
                    Callback<Books> callback);

    @GET("/result")
    void getNewspapers(@Query("key") String key,
                  @Query("encoding") String format,
                  @Query("n") String results,
                  @Query("q") String query,
                  @Query("zone") String zone,
                  @Query("l-category") String category,
                  Callback<Newspaper> callback);

    @GET("/result")
    void getPictures(@Query("key") String key,
                       @Query("encoding") String format,
                       @Query("n") String results,
                       @Query("q") String query,
                       @Query("zone") String zone,
                       @Query("l-format") String category,
                       Callback<Books> callback);

//    SINGLE RECORDS

    @GET("/contributor/{nuc}")
    void getLibrary(@Path("nuc") String nuc,
                    @Query("key") String key,
                    @Query("encoding") String format,
                    @Query("reclevel") String reclevel,
                    Callback<Libraries> callback);

    @GET("/newspaper/{id}")
    void getArticle(@Path("id") String id,
                    @Query("key") String key,
                    @Query("encoding") String format,
                    @Query("include") String include,
                    @Query("reclevel") String reclevel,
                    Callback<FullArticle> callback);

}
