package com.mvp.rx.sample.data.photos

import android.content.Context
import android.content.res.Resources
import com.mvp.rx.sample.data.Photo
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class PhotosLocalDataSource : IPhotosDataSource {

    override fun savePhotos(photos: List<Photo>): Completable = Completable.create {
        Realm.getDefaultInstance().executeTransactionAsync(
                { it.insertOrUpdate(photos) },
                { it.onComplete() },
                { error -> it.onError(error) }
        )
    }


    override fun getPhotos(context: Context): Single<List<Photo>> = Single.create<List<Photo>> {
        val photos = Realm.getDefaultInstance().where(Photo::class.java).findAll()
        if (photos != null) {
            it.onSuccess(photos)
        } else {
            it.onError(Resources.NotFoundException("Photos not found"))
        }

    }
}