package com.mvp.rx.sample.data.places

import android.content.Context
import com.mvp.rx.sample.data.Place
import com.mvp.rx.sample.webservice.IApi
import com.mvp.rx.sample.webservice.WebService
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class PlacesRemoteDataSource : IPlacesDataSource {

    override fun savePlaces(places: List<Place>): Completable = throw UnsupportedOperationException()

    override fun getPlaces(context: Context): Single<List<Place>> = WebService.createService(context, IApi::class.java).getPlaces()

}