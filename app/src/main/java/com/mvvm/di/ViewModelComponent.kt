package com.mvvm.di

import com.mvvm.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [APIModule::class])
interface ViewModelComponent
{
    fun inject(viewModel: ListViewModel)
}