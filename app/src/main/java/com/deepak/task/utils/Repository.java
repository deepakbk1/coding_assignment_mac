package com.deepak.task.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

/**
 * Created by deepakpurohit on 15,August,2019
 */
public class Repository {
    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<JsonElement> getMovies() {
        return apiCallInterface.getmovies(Urls.GET_MOVIES);
    }

}
