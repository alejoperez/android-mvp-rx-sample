package com.mvp.rx.sample.places

import com.mvp.rx.sample.R
import com.mvp.rx.sample.data.Place
import com.mvp.rx.sample.data.places.PlacesRepository

class PlacesPresenter(private val view: IPlacesContract.View): IPlacesContract.Presenter, PlacesRepository.IPlacesListener {

    override fun getPlaces() {
        PlacesRepository.getInstance().getPlaces(view.getViewContext(), this)
    }

    override fun onPlacesSuccess(places: List<Place>?) {
        if (view.isActive()) {
            view.onPlacesSuccess(places)
        }
    }

    override fun onPlacesFailure() {
        if (view.isActive()) {
            view.onPlacesFailure()
        }
    }

    override fun onNetworkError() {
        if (view.isActive()) {
            view.showAlert(R.string.error_network)
        }
    }
}