package com.mvvm.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mvvm.di.AppModule
import com.mvvm.di.DaggerViewModelComponent
import com.mvvm.model.Animal
import com.mvvm.model.AnimalApiService
import com.mvvm.model.ApiKey
import com.mvvm.util.SharedPreferencesHelper
import com.mvvm.view.ListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel (application: Application): AndroidViewModel(application)
{
    constructor(application: Application, test: Boolean = true): this(application)
    {
        injected = true
    }

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    private var invalidAPIKey = false
    private var injected = false

    fun inject()
    {
        if (!injected)
        {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    fun refresh()
    {
        inject()

        loading.value = true
        invalidAPIKey = false
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty())
        {
            getKey()
        }
        else
        {
            getAnimals(key)
        }
    }

    fun hardRefresh()
    {
        inject()

        loading.value = true
        getKey()
    }

    private fun getKey()
    {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread()) //to start the api service on a new thread
                .observeOn(AndroidSchedulers.mainThread()) // to get the results of the api service on the main thread
                .subscribeWith(object : DisposableSingleObserver<ApiKey>(){
                    override fun onSuccess(keyObject: ApiKey)
                    {
                        if (keyObject.key.isNullOrEmpty())
                        {
                            loadError.value = true
                            loading.value = false
                        }
                        else
                        {
                            prefs.saveApiKey(keyObject.key)
                            getAnimals(keyObject.key)
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String)
    {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(list: List<Animal>)
                    {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable)
                    {
                        if (!invalidAPIKey)
                        {
                            invalidAPIKey = true
                            getKey()
                        }
                        else
                        {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }

                })
        )
    }

    override fun onCleared()
    {
        super.onCleared()
        disposable.clear()
    }
}