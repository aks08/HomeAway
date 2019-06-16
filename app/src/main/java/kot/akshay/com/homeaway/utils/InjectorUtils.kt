package kot.akshay.com.homeaway.utils

import kot.akshay.com.homeaway.repository.PlaceRepository
import kot.akshay.com.homeaway.viewmodel.PlaceViewModelFactory

/**
 * utils class to provide dependency such as Dagger later on in methods
 */
object InjectorUtils {

    fun providePlacesRepository(): PlaceRepository {
        return PlaceRepository()
    }

     fun providePlaceViewModelFactory(): PlaceViewModelFactory {
        return PlaceViewModelFactory(providePlacesRepository())
    }
}