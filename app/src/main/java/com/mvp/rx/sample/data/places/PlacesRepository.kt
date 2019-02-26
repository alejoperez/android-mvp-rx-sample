package com.mvp.rx.sample.data.places

import android.content.Context
import com.mvp.rx.sample.data.Place
import io.reactivex.Completable
import io.reactivex.Single

class PlacesRepository private constructor(
        private val localDataSource: IPlacesDataSource = PlacesLocalDataSource(),
        private val remoteDataSource: IPlacesDataSource = PlacesRemoteDataSource()) : IPlacesDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PlacesRepository? = null

        fun getInstance(): PlacesRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PlacesRepository().also { INSTANCE = it }
        }
    }

    override fun getPlaces(context: Context): Single<List<Place>> {
        return if (hasCache) {
            localDataSource.getPlaces(context)
        } else {
            remoteDataSource.getPlaces(context).doOnSuccess {
                savePlaces(it)
            }
        }
    }

    override fun savePlaces(places: List<Place>): Completable = localDataSource.savePlaces(places)

    fun invalidateCache() {
        hasCache = false
    }
}