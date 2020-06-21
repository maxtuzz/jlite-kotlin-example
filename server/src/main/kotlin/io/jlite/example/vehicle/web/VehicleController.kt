package io.jlite.example.vehicle.web

import io.dinject.controller.*
import io.javalin.http.Context
import io.jlite.example.vehicle.api.ListResponse
import io.jlite.example.vehicle.api.Vehicle
import io.jlite.example.vehicle.service.VehicleService

@Controller
@Path("/vehicles")
class VehicleController(private val vehicleService: VehicleService) {
    /**
     * Get vehicles.
     * Return a list of all registered vehicles on the platform.
     */
    @Get
    fun getVehicles(ctx: Context): ListResponse<Vehicle> {
        return ListResponse(vehicleService.getVehicles().map { it.withHal(ctx) })
    }

    /**
     * Fetch a vehicle information by its number plate.
     * Return a singular vehicle entry.
     */
    @Get("/:numberPlate")
    fun getVehicle(numberPlate: String, ctx: Context): Vehicle {
        return vehicleService.getVehicle(numberPlate).withHal(ctx)
    }

    /**
     * Create new vehicle entry.
     * Create a new vehicle entry to be assigned to a driver.
     */
    @Post
    fun createVehicle(vehicle: Vehicle) {
        vehicleService.createVehicle(vehicle)
    }

    /**
     * Update a vehicle entry.
     * Update vehicle details including telemetry.
     */
    @Put("/:numberPlate")
    fun updateVehicle(numberPlate: String, vehicleUpdate: Vehicle) {
        vehicleService.updateVehicle(numberPlate, vehicleUpdate)
    }

    /**
     * Delete a vehicle.
     * Remove a vehicle entry.
     */
    @Delete("/:numberPlate")
    fun deleteVehicle(numberPlate: String) {
        vehicleService.delete(numberPlate)
    }
}