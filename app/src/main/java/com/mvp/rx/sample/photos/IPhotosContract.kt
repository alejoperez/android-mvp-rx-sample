package com.mvp.rx.sample.photos

import com.mvp.rx.sample.base.IBaseView
import com.mvp.rx.sample.data.Photo

interface IPhotosContract {

    interface View: IBaseView {
        fun onPhotosSuccess(photos: List<Photo>?)
        fun onPhotosFailure()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun getPhotos()
    }
}