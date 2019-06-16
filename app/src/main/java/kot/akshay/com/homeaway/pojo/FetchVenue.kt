package kot.akshay.com.homeaway.pojo

class FetchVenue {

    // The ID of the venue.
    internal var id: String? = null

    // The name of the venue.
    internal var name: String? = null

    // The rating of the venue, if available.
    internal var rating: Double = 0.toDouble()

    // A location object within the venue.
    internal var location: FetchLocation? = null

    internal var categories: List<FetchCategories>? = null
}
