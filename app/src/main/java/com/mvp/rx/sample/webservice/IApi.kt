package com.mvp.rx.sample.webservice

import com.mvp.rx.sample.data.Photo
import com.mvp.rx.sample.data.Place
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApi {

    @POST("user/login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @POST("user/register")
    fun register(@Body request: RegisterRequest): Single<RegisterResponse>

    @GET("places")
    fun getPlaces(): Single<List<Place>>

    @GET("photos")
    fun getPhotos(): Single<List<Photo>>

}