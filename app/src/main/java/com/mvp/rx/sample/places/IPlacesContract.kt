package com.mvp.rx.sample.places

import com.mvp.rx.sample.base.IBaseView
import com.mvp.rx.sample.data.Place

interface IPlacesContract {

    interface View: IBaseView {
        fun onPlacesSuccess(places: List<Place>)
        fun onPlacesFailure()
    }

    interface Presenter {
        fun getPlaces()
    }
}