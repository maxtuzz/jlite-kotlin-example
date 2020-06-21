package io.jlite.example.vehicle.web

import io.dinject.controller.*
import io.javalin.http.Context
import io.jlite.example.vehicle.api.Driver
import io.jlite.example.vehicle.api.ListResponse
import io.jlite.example.vehicle.service.DriverService

@Controller
@Path("/drivers")
class DriverController(private val driverService: DriverService) {
    /**
     * Get all drivers.
     * Returns a list of drivers based on search parameters.
     */
    @Get
    fun get(ctx: Context): ListResponse<Driver> {
        return ListResponse(driverService.getDrivers().map { it.withHal(ctx) })
            .withHal(ctx)
    }

    /**
     * Get a driver by their license.
     * Fetches data for a specific driver based on their drivers license number.
     */
    @Get("/:licenseNumber")
    fun getDriver(licenseNumber: String, ctx: Context): Driver {
        return driverService.getDriver(licenseNumber).withHal(ctx)
    }

    /**
     * Create new driver.
     * Creates a new driver entry to be assigned to a vehicle or vehicles.
     */
    @Post
    fun createDriver(driver: Driver) {
        driverService.createDriver(driver.name, driver.licenseNumber)
    }

    /**
     * Updates an existing driver.
     * Update driver details including their license details, and their name.
     */
    @Put("/:licenseNumber")
    fun updateDriver(licenseNumber: String, driver: Driver) {
        driverService.updateDriver(licenseNumber, driver)
    }

    /**
     * Delete a driver.
     * Removed a driver details from the system.
     */
    @Delete("/:licenseNumber")
    fun deleteDriver(licenseNumber: String) {
        driverService.deleteDriver(licenseNumber)
    }
}