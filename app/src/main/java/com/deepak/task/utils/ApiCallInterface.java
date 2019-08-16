package com.deepak.task.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by deepakpurohit on 15,August,2019
 */
public interface ApiCallInterface {

    @GET
    Observable<JsonElement> getmovies(@Url String url) ;
}
