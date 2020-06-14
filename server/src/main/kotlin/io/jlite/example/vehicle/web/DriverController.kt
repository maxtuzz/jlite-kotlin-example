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
     */
    @Get
    fun get(ctx: Context): ListResponse<Driver> {
        return ListResponse(driverService.getDrivers())
            .withHal(ctx)
    }

    /**
     * Get a driver by their license.
     */
    @Get("/:licenseNumber")
    fun getDriver(licenseNumber: String, ctx: Context): Driver {
        return driverService.getDriver(licenseNumber)
    }

    /**
     * Create new driver.
     */
    @Post
    fun createDriver(driver: Driver) {
        driverService.createDriver(driver.name, driver.licenseNumber)
    }

    /**
     * Updates an existing driver.
     */
    @Put("/:licenseNumber")
    fun updateDriver(licenseNumber: String, driver: Driver) {
        driverService.updateDriver(licenseNumber, driver)
    }

    /**
     * Delete a driver.
     */
    @Delete("/:licenseNumber")
    fun deleteDriver(licenseNumber: String) {
        driverService.deleteDriver(licenseNumber)
    }
}