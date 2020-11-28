package com.mvvm.di

import com.mvvm.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent
{
    fun inject(viewModel: ListViewModel)
}