package com.mvp.rx.sample.data.places

import android.content.Context
import com.mvp.rx.sample.data.Place

interface IPlacesDataSource {

    fun getPlaces(context: Context, listener: PlacesRepository.IPlacesListener)

    fun savePlaces(places: List<Place>)
}