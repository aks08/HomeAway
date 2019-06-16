package kot.akshay.com.homeaway.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import kot.akshay.com.homeaway.adapter.PlaceDetailAdapter
import kot.akshay.com.homeaway.pojo.FetchVenue
import kot.akshay.com.homeaway.utils.Constants
import kot.akshay.com.homeaway.utils.InjectorUtils
import kot.akshay.com.homeaway.utils.showToast
import kot.akshay.com.homeaway.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.activity_placelisting.*

/**
 * Activity to listen changes in data with the help of Live Data observer and get
 * the data from the VIewModel.
 */
@Suppress("DEPRECATION")
class PlaceListingActivity : AppCompatActivity() {
    private var placeListingManager: LinearLayoutManager? = null
    private var placeDetailAdapter: RecyclerView.Adapter<*>? = null

    private var placeListingRecyclerview: RecyclerView? = null
    private var searchString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(kot.akshay.com.homeaway.R.layout.activity_placelisting)
        startLoader()
        extractArguments()
        setToolbar()
        setRecyclerview()
        setAndObserveLiveData()
    }

    private fun extractArguments() {
        if (intent != null && intent.extras != null)
            searchString = intent.getStringExtra(Constants.SEARCH_QUERY)
    }

    private fun setToolbar() {
        listingToolbar.title = searchString
        setSupportActionBar(listingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecyclerview() {
        placeListingRecyclerview = findViewById(kot.akshay.com.homeaway.R.id.id_rv_placeList)
        placeListingRecyclerview?.setHasFixedSize(true)
        placeListingRecyclerview?.isMotionEventSplittingEnabled = false
        placeListingManager = LinearLayoutManager(this)
        placeListingRecyclerview?.layoutManager = placeListingManager

    }

    /**
     * Get data from the factory and observe for the changes
     */
    private fun setAndObserveLiveData() {
        var factory = InjectorUtils.providePlaceViewModelFactory()
        var mViewModel = ViewModelProviders.of(this, factory).get(PlaceViewModel::class.java)

        mViewModel.fetchSearchPlaces(
            getString(kot.akshay.com.homeaway.R.string.foursquare_client_id), getString(
                kot.akshay.com.homeaway.R.string.foursquare_client_secret
            ), searchString
        ).observe(this, Observer<List<FetchVenue>> {
            stopLoaderAndSetAnimation()
            if (it != null) {
                if (it.isNotEmpty()) {
                    placeDetailAdapter = PlaceDetailAdapter(this, it)
                    placeListingRecyclerview?.adapter = placeDetailAdapter
                } else {
                    showToast(this, Constants.NO_VENUES)
                    finish()
                }
            } else {
                showToast(this, Constants.SEARCH_PLACE)
                finish()
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startLoader() {
        av_from_code?.visibility = View.VISIBLE
        av_from_code?.setAnimation("wave.json")
        av_from_code?.loop(true)
        av_from_code?.playAnimation()
    }

    private fun stopLoaderAndSetAnimation() {
        av_from_code?.cancelAnimation()
        av_from_code?.visibility = View.GONE
        val resId = kot.akshay.com.homeaway.R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(this, resId)
        placeListingRecyclerview?.setLayoutAnimation(animation)
    }
}