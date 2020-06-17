package io.jlite.example.vehicle.api

import io.javalin.http.Context

/**
 * Generates links to REST resources
 *
 * TODO: Test how this works behind a load balancer
 *  - may need to use ctx to check for x_forwarded headers
 */
class HalBuilder(private val ctx: Context) {
    private val links: MutableMap<String, HalLink> = mutableMapOf()

    private val proto = if (ctx.protocol().endsWith("s")) {
        "https"
    } else {
        "http"
    }

    private val baseUrl = "${proto}://${ctx.host()}"
    private val vehicleBase = "${baseUrl}/vehicles"
    private val driversBase = "${baseUrl}/drivers"

    fun toContextPath(propertyName: String): HalBuilder {
        links[propertyName] = HalLink(ctx.fullUrl())

        return this
    }

    fun toVehicleBase(): HalBuilder {
        links["vehicles"] = HalLink(vehicleBase)

        return this
    }

    fun toVehicle(propertyName: String, plateNumber: String): HalBuilder {
        links[propertyName] = HalLink("$vehicleBase/$plateNumber")

        return this
    }

    fun toDriverBase(): HalBuilder {
        links["drivers"] = HalLink(driversBase)

        return this
    }

    fun toDriver(propertyName: String, licenseNumber: String): HalBuilder {
        links[propertyName] = HalLink("$driversBase/$licenseNumber")

        return this
    }

    fun toMessages(propertyName: String, driversLicense: String): HalBuilder {
        links[propertyName] = HalLink("$driversBase/$driversLicense/messages")

        return this
    }

    fun build(): MutableMap<String, HalLink> {
        return links
    }
}