package com.amir.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amir.dogs.model.DogApiService
import com.amir.dogs.model.DogBreed
import com.amir.dogs.model.DogDatabse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val dogService = DogApiService()

    //allow us to retrieve(observe) the observable that the API gives (the single) .
    //allows avoid any memory leaks
    private val disposable = CompositeDisposable()

    //retrieve data from api
    val dogs = MutableLiveData<List<DogBreed>>()

    //if an error accrued
    val dogsLoadError = MutableLiveData<Boolean>()

    // by loading showing the progressbar
    val loading = MutableLiveData<Boolean>()

    //refresh the information
    fun refresh() {
        fetchFromRemote()
    }

    //return data from the remote endpoint (retrieve data from API)
    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            //it returns the single that we create in DogApiService
            //set the goal to be on separate thread(not an main thread)
            //Schedulers.newThread() it says i want this action be done in background thread
            dogService.getDogs().subscribeOn(Schedulers.newThread())
                //the result process of last action must be processed on the Main thread
                //in order to display it we need the main thread not background thread
                .observeOn(AndroidSchedulers.mainThread())
                //here we pass the observer
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocally(dogList)
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun dogsRetrieved(dogList: List<DogBreed>) {
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
//coroutine are for us to access the database from seperate thread
        //database cannot be access from the main thread
        launch {
            val dao = DogDatabse(getApplication()).dogDao()
          dao.deleteAllDogs()
            //*list.toTypedArray() : it gets a list and expand it to an individual element
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size){
                list[i].uuid = result[i].toInt()
            }
            dogsRetrieved(list)
        }
    }

    //how to manage disposable to avoid memory leaks
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}