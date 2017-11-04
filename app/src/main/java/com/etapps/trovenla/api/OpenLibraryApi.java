package com.etapps.trovenla.api;

//import com.etapps.trovenla.models.ol.Book;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by emanuele on 22/03/17.
 */

public interface OpenLibraryApi {

    //https://openlibrary.org/api/books?bibkeys=ISBN:9780980200447&jscmd=data&format=json

//    @GET("/books")
//    Call<Map<String, Book>> getBook(@Query("bibkeys") String key,
//                                    @Query("jscmd") String type,
//                                    @Query("format") String format);
}
