package kot.akshay.com.homeaway.adapter

import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kot.akshay.com.homeaway.R
import kot.akshay.com.homeaway.pojo.FetchVenue
import kot.akshay.com.homeaway.ui.MapsActivity
import kot.akshay.com.homeaway.utils.Constants
import kot.akshay.com.homeaway.utils.loadImage


/**
 * The context for getting resources
 * The list of results from the Foursquare API
 */
class PlaceDetailAdapter(
    private val context: Context,
    private val results: List<FetchVenue>?
) : RecyclerView.Adapter<PlaceDetailAdapter.PlaceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(kot.akshay.com.homeaway.R.layout.row_place_list, parent, false)
        return PlaceHolder(view)
    }

    override fun getItemCount(): Int {
        return if (results != null && results.isNotEmpty())
            results?.size
        else
            0
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {

        // Sets each view with the appropriate venue details
        try {
            var fetchVenue = results?.get(position)
            holder.name.text = fetchVenue?.name
            holder.category.text = fetchVenue?.categories?.get(0)?.name
            holder.address.text = fetchVenue?.location?.address
            holder.distance.text = String.format(
                "%.0f",
                (calculateDistance(fetchVenue?.location?.lat, fetchVenue?.location?.lng))
            ) + " m"
            holder.icon.loadImage(
                fetchVenue?.categories?.get(0)?.icon?.prefix + "64" + fetchVenue?.categories?.get(0)?.icon?.suffix,
                context
            )

            // Stores additional venue details for the map view
            holder.id = fetchVenue?.id
            holder.latitude = fetchVenue?.location?.lat!!
            holder.longitude = fetchVenue?.location?.lng!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    class PlaceHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        // The venue fields to display
        internal var name: TextView
        internal var address: TextView
        internal var category: TextView
        internal var distance: TextView
        internal var icon: ImageView
        internal var id: String? = null
        internal var latitude: Double = 0.toDouble()
        internal var longitude: Double = 0.toDouble()

        init {
            v.setOnClickListener(this)

            // Gets the appropriate view for each venue detail
            name = v.findViewById(R.id.id_tv_name)
            address = v.findViewById(R.id.id_tv_address)
            category = v.findViewById(R.id.id_tv_category)
            distance = v.findViewById(R.id.id_tv_distance)
            icon = v.findViewById(R.id.id_iv_icon)
        }

        override fun onClick(v: View) {

            // Creates an intent to direct the user to a map view
            val context = name.context
            val i = Intent(context, MapsActivity::class.java)

            // Passes the  venue details onto the map view
            i.putExtra(Constants.NAME, name.text)
            i.putExtra(Constants.ID, id)
            i.putExtra(Constants.LATITUDE, latitude)
            i.putExtra(Constants.LONGITUDE, longitude)

            // Transitions to the map view.
            context.startActivity(i)
        }

    }

    fun calculateDistance(lat1: Double?, lng1: Double?): Float {
        val startPoint = Location("locationA")
        /**
         * seattle latlng
         */
        startPoint.latitude = 47.608013
        startPoint.longitude = -122.335167

        /**
         * destination latlng
         */
        val endPoint = Location("locationA")
        lat1?.let { endPoint.latitude = it }
        lng1?.let { endPoint.longitude = it }

        return startPoint.distanceTo(endPoint)
    }

}


