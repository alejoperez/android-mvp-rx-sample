package com.mvp.rx.sample.data.photos

import android.content.Context
import com.mvp.rx.sample.data.Photo
import io.reactivex.Completable
import io.reactivex.Single

class PhotosRepository private constructor(
        private val localDataSource: IPhotosDataSource = PhotosLocalDataSource(),
        private val remoteDataSource: IPhotosDataSource = PhotosRemoteDataSource()) : IPhotosDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PhotosRepository? = null

        fun getInstance(): PhotosRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PhotosRepository().also { INSTANCE = it }
        }
    }

    override fun getPhotos(context: Context): Single<List<Photo>> {
        return if (hasCache) {
            localDataSource.getPhotos(context)
        } else {
            remoteDataSource.getPhotos(context)
                    .doOnSuccess {
                        savePhotos(it)
                    }
        }
    }

    override fun savePhotos(photos: List<Photo>): Completable = localDataSource.savePhotos(photos)

    fun invalidateCache() {
        hasCache = false
    }
}