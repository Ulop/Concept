package io.ulop.concept.data

class Person(
        val id: String,
        val name: String,
        val surname: String,
        val avatar: String,
        val about: String,
        val place: String,
        val alerts: MutableList<Alert> = mutableListOf(),
        val places: List<Place> = listOf(),
        val shots: MutableList<Shot> = mutableListOf(),
        val friends: MutableList<Person> = mutableListOf()
){
    override fun toString(): String {
        return "$name $surname, from $place, visited ${places.size} places"
    }
}

class Alert
class Place(val name: String, description: String = "", location: Location = 0.0 to 0.0)
class Shot(val url: String, val color: Int)
typealias Location = Pair<Double, Double>