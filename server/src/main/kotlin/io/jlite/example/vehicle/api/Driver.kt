package io.jlite.example.vehicle.api

import io.javalin.http.Context

/**
 * DTO bean for driver restful representation
 */
open class Driver(val name: String, val licenseNumber: String): HalResource<Driver>() {
    override fun withHal(ctx: Context): Driver {
        TODO("Not yet implemented")
    }
}