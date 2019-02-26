package com.mvp.rx.sample.data.places

import android.content.Context
import android.content.res.Resources
import com.mvp.rx.sample.data.Place
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class PlacesLocalDataSource : IPlacesDataSource {

    override fun savePlaces(places: List<Place>): Completable = Completable.create {
        Realm.getDefaultInstance().executeTransactionAsync(
                { it.insertOrUpdate(places) },
                { it.onComplete() },
                { error -> it.onError(error) }
        )
    }

    override fun getPlaces(context: Context): Single<List<Place>> = Single.create<List<Place>> {
        val places = Realm.getDefaultInstance().where(Place::class.java).findAll()
        if (places != null) {
            it.onSuccess(places)
        } else {
            it.onError(Resources.NotFoundException("Places not found"))
        }
    }
}
