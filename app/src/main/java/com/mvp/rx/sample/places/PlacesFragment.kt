package com.mvp.rx.sample.places

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BaseFragment
import com.mvp.rx.sample.data.Place
import kotlinx.android.synthetic.main.fragment_places.*

class PlacesFragment: BaseFragment(), OnMapReadyCallback, IPlacesContract.View {

    companion object {
        private const val ZOOM = 4f
        const val TAG = "PlacesFragment"
        fun newInstance() = PlacesFragment()
    }

    private val presenter by lazy { PlacesPresenter(this) }

    private lateinit var googleMap: GoogleMap

    private var currentPlaces: List<Place>? = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_places,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMapPlaces) as SupportMapFragment
        mapFragment.getMapAsync(this)

        placesFabRandom.setOnClickListener {
            fabView -> randomPlace(); Snackbar.make(fabView, R.string.places_random_message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onMapReady(gm: GoogleMap) {
        googleMap = gm
        presenter.getPlaces()
    }

    override fun onPlacesFailure() {
        showAlert(R.string.error_loading_places)
    }

   override fun onPlacesSuccess(places: List<Place>) {
       currentPlaces = places
       loadPlacesInMap()
       randomPlace()
    }

    private fun loadPlacesInMap() {
        currentPlaces?.let {
            for (p in it) {
                googleMap.addMarker(buildMarkerPlace(p))
            }
        }
    }

    private fun buildMarkerPlace(p: Place): MarkerOptions {
        return MarkerOptions()
                .position(LatLng(p.lat, p.lon))
                .title(p.companyName)
                .snippet(p.address)
    }

    private fun randomPlace() {
        currentPlaces?.let {
            if (it.isNotEmpty()) {
                val randomPosition = (0 until it.size).shuffled().first()
                val place = it[randomPosition]
                val cameraPosition = CameraPosition.Builder().target(LatLng(place.lat, place.lon)).zoom(ZOOM).build()
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }

}