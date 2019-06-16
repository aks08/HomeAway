package kot.akshay.com.homeaway.pojo

class FetchLocation {

    // The address of the location.
    internal var address: String? = null

    // The latitude of the location.
    internal var lat: Double = 0.toDouble()

    // The longitude of the location.
    internal var lng: Double = 0.toDouble()

    // The distance of the location, calculated from the specified location.
    internal var distance: Int = 0

}
