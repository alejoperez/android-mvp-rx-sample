package com.mvp.rx.sample.data.photos

import android.content.Context
import com.mvp.rx.sample.data.Photo
import com.mvp.rx.sample.extensions.enqueue
import com.mvp.rx.sample.webservice.IApi
import com.mvp.rx.sample.webservice.WebService
import java.lang.UnsupportedOperationException

class PhotosRemoteDataSource : IPhotosDataSource {

    override fun savePhotos(photos: List<Photo>) = throw UnsupportedOperationException()

    override fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener) {
        val service = WebService.createService(context, IApi::class.java)
        val call = service.getPhotos()
        call.enqueue(
                { response ->
                    if (response.isSuccessful) {
                        listener.onPhotosSuccess(response.body())

                    } else {
                        listener.onPhotosFailure()
                    }
                },
                {
                    listener.onNetworkError()
                }
        )
    }

}