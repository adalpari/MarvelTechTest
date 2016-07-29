package com.github.adalpari.mvpretrofitjson.utils;

import com.github.adalpari.mvpretrofitjson.model.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by plaza.a on 28/07/2016.
 */
public interface ApiEndPointInterface {

    // http://gateway.marvel.com:80/v1/public/characters/1009220/comics?limit=10&offset=0&apikey=

    @GET("/v1/public/characters/{characterId}/comics")
    Call<APIResponse> getComics(@Path("characterId") int characterId,
                                @Query("limit") int limit,
                                @Query("offset") int offset,
                                @Query("apikey") String apikey,
                                @Query("ts") Long ts,
                                @Query("hash") String hast);

}
