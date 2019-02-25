package com.mvp.rx.sample.data.photos

import android.content.Context
import com.mvp.rx.sample.data.Photo

interface IPhotosDataSource {

    fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener)

    fun savePhotos(photos: List<Photo>)

}