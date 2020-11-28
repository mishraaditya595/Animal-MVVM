package com.mvvm

import com.mvvm.di.APIModule
import com.mvvm.model.AnimalApiService

class APIModuleTest(val mockService: AnimalApiService): APIModule()
{
    override fun provideAnimalApiService(): AnimalApiService
    {
        return mockService
    }
}