package kot.akshay.com.homeaway.repository

import android.arch.lifecycle.MutableLiveData
import com.akki.cineholic.rest.RetrofitClient
import kot.akshay.com.homeaway.pojo.FetchJSON
import kot.akshay.com.homeaway.pojo.FetchVenue
import retrofit2.Call
import retrofit2.Response

/**
 * Repository class to deliver the data to view model class
 */
class PlaceRepository {

    /**
     * method to fetch venues as per user query
     *
     * clientId: foursquare clientId
     * clientSecret: foursquare clientSecret
     * searchQuery: users entered data
     */

    fun fetchPlaces(clientId: String, clientSecret: String, searchQuery: String): MutableLiveData<List<FetchVenue>> {

        var coffeeCall = RetrofitClient.instance.apiService.search(
            clientId, clientSecret,
          "Seattle,+WA", searchQuery,20
        )
        val op = MutableLiveData<List<FetchVenue>>()
        coffeeCall.enqueue(
            object : retrofit2.Callback<FetchJSON> {

                override fun onFailure(call: Call<FetchJSON>, t: Throwable) {
                    op.value = null
                }

                override fun onResponse(call: Call<FetchJSON>, response: Response<FetchJSON>) {
                    // Gets the venue object from the JSON response
                    val fjson = response.body()
                    val fr = fjson?.response
                    val frs = fr?.venues
                    op.value = frs
                }

            })
        return op
    }
}