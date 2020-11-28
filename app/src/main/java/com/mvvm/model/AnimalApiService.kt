package com.mvvm.model

import com.mvvm.di.DaggerAPIComponent
import io.reactivex.Single
import javax.inject.Inject

class AnimalApiService
{
    @Inject
    lateinit var api: AnimalApi

    init
    {
        DaggerAPIComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey>
    {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>>
    {
        return api.getAnimals(key)
    }
}