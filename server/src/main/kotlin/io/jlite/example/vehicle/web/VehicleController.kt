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
     * Get vehicles
     */
    @Get
    fun getVehicles(ctx: Context): ListResponse<Vehicle> {
        return ListResponse(vehicleService.getVehicles())
    }

    /**
     * Fetch a vehicle information by it's number plate
     */
    @Get("/:numberPlate")
    fun getVehicle(numberPlate: String, ctx: Context): Vehicle {
        return vehicleService.getVehicle(numberPlate)
    }

    /**
     * Create new vehicle entry
     */
    @Post
    fun createVehicle(vehicle: Vehicle) {
        vehicleService.createVehicle(vehicle)
    }

    /**
     * Update a vehicle entry.
     */
    @Put("/:numberPlate")
    fun updateVehicle(numberPlate: String, vehicleUpdate: Vehicle) {
        vehicleService.updateVehicle(numberPlate, vehicleUpdate)
    }

    /**
     * Delete a vehicle
     */
    @Delete("/:numberPlate")
    fun deleteVehicle(numberPlate: String) {
        vehicleService.delete(numberPlate)
    }
}