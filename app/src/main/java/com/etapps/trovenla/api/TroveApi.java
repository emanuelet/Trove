package com.etapps.trovenla.api;

import com.etapps.trovenla.models.articles.FullArticle;
import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.library.Library;
import com.etapps.trovenla.models.newspapers.Newspaper;
import com.etapps.trovenla.models.queries.Books;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by emanuele on 15/07/15.
 */
public interface TroveApi {

//    QUERIES

    @GET("/contributor")
    Call<Libraries> getLibraries(@Query("key") String key,
                                 @Query("encoding") String format,
                                 @Query("reclevel") String reclevel);

    @GET("/result")
    Call<Books> getContent(@Query("key") String key,
                           @Query("encoding") String format,
                           @Query("n") String results,
                           @Query("q") String query,
                           @Query("zone") String zone,
                           @Query("include") String include);

    @GET("/result")
    Call<Newspaper> getNewspapers(@Query("key") String key,
                                  @Query("encoding") String format,
                                  @Query("n") String results,
                                  @Query("q") String query,
                                  @Query("zone") String zone,
                                  @Query("l-category") String category);

    @GET("/result")
    Call<Books> getPictures(@Query("key") String key,
                            @Query("encoding") String format,
                            @Query("n") String results,
                            @Query("q") String query,
                            @Query("zone") String zone,
                            @Query("l-format") String category);

//    SINGLE RECORDS

    @GET("/contributor/{nuc}")
    Call<Library> getLibrary(@Path("nuc") String nuc,
                             @Query("key") String key,
                             @Query("encoding") String format,
                             @Query("reclevel") String reclevel);

    @GET("/newspaper/{id}")
    Call<FullArticle> getArticle(@Path("id") String id,
                                 @Query("key") String key,
                                 @Query("encoding") String format,
                                 @Query("include") String include,
                                 @Query("reclevel") String reclevel);

}
