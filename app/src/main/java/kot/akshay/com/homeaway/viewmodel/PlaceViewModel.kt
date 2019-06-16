package kot.akshay.com.homeaway.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kot.akshay.com.homeaway.pojo.FetchResults
import kot.akshay.com.homeaway.pojo.FetchVenue
import kot.akshay.com.homeaway.repository.PlaceRepository

/**
 *  ViewModel class to fetch data and deliver the data to its listener
 */
class PlaceViewModel(private val placeRepository: PlaceRepository) : android.arch.lifecycle.ViewModel(){

    var searchPlaces : LiveData<List<FetchResults>> = MutableLiveData<List<FetchResults>>()


    fun fetchSearchPlaces(clientId:String,clientSecret:String, searchString: String):LiveData<List<FetchVenue>>{
       return placeRepository.fetchPlaces(clientId,clientSecret,searchString)
    }

}