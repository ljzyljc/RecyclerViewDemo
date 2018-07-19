package com.finance.recyclerviewdemo.rxjava;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Jackie on 2018/7/16.
 */
public interface Api {

    @GET
    Observable<User> login(@Body User user);

    @GET
    Observable<LoginResponse> register(@Body LoginResponse request);

}
