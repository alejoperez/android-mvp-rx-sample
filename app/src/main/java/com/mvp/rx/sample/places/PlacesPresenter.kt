package com.mvp.rx.sample.places

import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.Place
import com.mvp.rx.sample.data.places.PlacesRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads

class PlacesPresenter(private val view: IPlacesContract.View): BasePresenter(), IPlacesContract.Presenter {

    override fun getPlaces() {
        PlacesRepository.getInstance().getPlaces(view.getViewContext())
                .applyIoAndMainThreads()
                .subscribe(
                        { onPlacesSuccess(it) },
                        { onPlacesFailure() }
                )
                .addTo(compositeDisposable)
    }

    private fun onPlacesSuccess(places: List<Place>) {
        if (view.isActive()) {
            view.onPlacesSuccess(places)
        }
    }

    private fun onPlacesFailure() {
        if (view.isActive()) {
            view.onPlacesFailure()
        }
    }
}