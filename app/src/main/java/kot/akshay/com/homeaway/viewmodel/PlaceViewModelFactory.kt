package kot.akshay.com.homeaway.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kot.akshay.com.homeaway.repository.PlaceRepository

/**
 * Factory class to initialize constructor
 */
class PlaceViewModelFactory(private val placesRepository: PlaceRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceViewModel(placesRepository) as T
    }
}
