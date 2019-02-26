package com.mvp.rx.sample.photos

import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.Photo
import com.mvp.rx.sample.data.photos.PhotosRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads

class PhotosPresenter(private val view: IPhotosContract.View): BasePresenter(), IPhotosContract.Presenter {

    override fun getPhotos() {
        view.showProgress()
        PhotosRepository.getInstance().getPhotos(view.getViewContext())
                .applyIoAndMainThreads()
                .subscribe(
                        {onPhotosSuccess(it)},
                        {onPhotosFailure()}
                )
                .addTo(compositeDisposable)
    }

    private fun onPhotosSuccess(photos: List<Photo>) {
        if (view.isActive()) {
            view.hideProgress()
            view.onPhotosSuccess(photos)
        }
    }

    private fun onPhotosFailure() {
        if (view.isActive()) {
            view.hideProgress()
            view.onPhotosFailure()
        }
    }
}