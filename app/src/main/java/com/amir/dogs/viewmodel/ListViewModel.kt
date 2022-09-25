package com.amir.dogs.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amir.dogs.model.DogApiService
import com.amir.dogs.model.DogBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
class ListViewModel : ViewModel() {

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
                        dogs.value=dogList
                        dogsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                       dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    //how to manage disposable to avoid memory leaks
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}