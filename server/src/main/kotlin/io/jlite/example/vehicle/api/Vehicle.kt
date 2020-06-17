package io.jlite.example.vehicle.api

import io.javalin.http.Context
import io.jlite.example.vehicle.domain.DVehicle
import io.jlite.example.vehicle.domain.VehicleTelematics

open class Vehicle(
    val driverLicense: String,
    val plateNumber: String,
    val make: String,
    val model: String,
    val year: String,
    val telematics: VehicleTelematics
) : HalResource<Vehicle>() {

    // Create a new dto entry from entity bean
    constructor(dVehicle: DVehicle) : this(
        dVehicle.driver.licenseNumber,
        dVehicle.plateNumber,
        dVehicle.make,
        dVehicle.model,
        dVehicle.year,
        dVehicle.telematics
    )

    override fun withHal(ctx: Context): Vehicle {
        links.addAll {
            HalBuilder(ctx)
                .toVehicle("self", plateNumber)
                .toDriver("driver", driverLicense)
                .build()
        }

        return this
    }
}