package io.jlite.example.vehicle.domain

/**
 * Not stored as a table but rather as document
 */
class VehicleTelematics(
    val pressure: String? = "1psi",
    temperature: String = "20c",
    val speed: String? = "0km",
    val latitude: String? = "-36.848461",
    val longitude: String? = "174.763336") {

    var temperature = temperature + "c"
}
