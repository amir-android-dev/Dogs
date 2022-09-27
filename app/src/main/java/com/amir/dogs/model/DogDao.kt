package com.amir.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    //var arg: short form of variable argument
    //list<Long> is the size of primary
    //suspend it will be run on second thread
    @Insert
    suspend fun insertAll(vararg dogs:DogBreed):List<Long>

    @Query("select * from dogbreed")
    suspend fun getAllDogs():List<DogBreed>

    @Query("select * from dogbreed where uuid = :dogId")
    suspend fun getDog(dogId:Int) : DogBreed

    @Query("delete from dogbreed")
    suspend fun deleteAllDogs()


}