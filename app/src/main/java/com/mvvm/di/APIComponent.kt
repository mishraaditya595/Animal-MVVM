package com.mvvm.di

import com.mvvm.model.AnimalApiService
import dagger.Component

@Component(modules = [APIModule::class])
interface APIComponent
{
    fun inject(service: AnimalApiService)
}