package com.mvvm.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi
{
    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    @POST("getAnimals")
    fun getAnimals(@Field("key") key:String): Single<List<Animal>>
}