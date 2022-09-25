package com.amir.dogs.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class DogApiService {

    private val BASE_URL = "https://raw.githubusercontent.com/"


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
            //It converts a list of our elements(object) into an observable,
            //we need it to convert the data from  JSON to single
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
        .build()
        .create(DogsAPI::class.java)


    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }
}