package kot.akshay.com.homeaway.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kot.akshay.com.homeaway.utils.Constants
import kotlinx.android.synthetic.main.activity_scrolling.*


/**
 * Activity to show venue distance from the center of the seattle
 * in map. Also, showing the venue details.
 */
@Suppress("DEPRECATION")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var markerPoints = ArrayList<LatLng>()
    // The details of the venue that is being displayed.
    private var venueID: String? = null
    private var venueName: String? = null
    private var venueLatitude: Double = 0.toDouble()
    private var venueLongitude: Double = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(kot.akshay.com.homeaway.R.layout.activity_scrolling)
        setToolbar()
        extractArguments()
        setMap()
    }

    private fun extractArguments() {
        if (intent != null && intent.extras != null) {
            val venue = intent.extras
            venueID = venue?.getString(Constants.ID)
            venueName = venue.getString(Constants.NAME)
            venueLatitude = venue.getDouble(Constants.LATITUDE)
            venueLongitude = venue.getDouble(Constants.LONGITUDE)
            title = venueName
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

    }

    /**
     *   Obtain the SupportMapFragment and get notified when the map is ready to be used.
     */
    private fun setMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(kot.akshay.com.homeaway.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * handle toolbar click here
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latlngDestination = LatLng(venueLatitude, venueLongitude)
        val latlngSource = LatLng(47.608013, -122.335167) // seattle lat lng
        markerPoints.add(latlngDestination)
        markerPoints.add(latlngSource)

        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
        for (z in 0 until markerPoints.size) {
            val point = markerPoints[z]
            options.add(point)
        }
        mMap.let {
            it.mapType = GoogleMap.MAP_TYPE_NORMAL
            it.addPolyline(options)
            it.addMarker(MarkerOptions().position(latlngDestination))?.title=title?.toString()
            it.addMarker(MarkerOptions().position(latlngSource))?.title="Seattle center"
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isRotateGesturesEnabled = false
            it.uiSettings.isScrollGesturesEnabled = true
            it.uiSettings.isTiltGesturesEnabled = false
        }


        // Show map at center of screen
        mMap?.setOnCameraChangeListener {
            var builder = LatLngBounds.Builder()
            for (i in 0 until markerPoints.size) {
                builder.include(markerPoints[i])
            }

            var bounds = builder.build()
            var cu = CameraUpdateFactory.newLatLngBounds(bounds,200)
            try {
                mMap?.moveCamera(cu)
                mMap?.animateCamera(cu)
            } catch (e: Exception) {
                e.printStackTrace().toString()
            }
            // Remove listener to prevent position reset on camera move.
            mMap?.setOnCameraChangeListener(null)
        }

    }

}
