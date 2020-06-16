package io.jlite.example.vehicle.service

import io.javalin.http.NotFoundResponse
import io.jlite.example.vehicle.api.Vehicle
import io.jlite.example.vehicle.domain.DVehicle
import io.jlite.example.vehicle.domain.query.QDVehicle
import javax.inject.Singleton

@Singleton
class VehicleService(private val driverService: DriverService) {
    /**
     * Create a vehicle entry
     */
    fun createVehicle(vehicle: Vehicle) {
        val dDriver = driverService.findByLicense(vehicle.driverLicense)

        DVehicle(
            dDriver,
            vehicle.plateNumber,
            vehicle.make,
            vehicle.model,
            vehicle.year,
            vehicle.telematics
        ).save()
    }

    /**
     * Returns a vehicle entity bean with specified plate number
     */
    fun getVehicle(plateNumber: String): Vehicle {
        val dVehicle = findVehicleByPlate(plateNumber)
        return Vehicle(dVehicle)
    }

    private fun findVehicleByPlate(plateNumber: String): DVehicle {
        return QDVehicle()
            .plateNumber.eq(plateNumber)
            .findOne() ?: throw NotFoundResponse("Cannot find a vehicle with that rego")
    }

    /**
     * Returns all vehicle entity beans
     */
    fun getVehicles(): List<Vehicle> {
        return QDVehicle().findList().map { Vehicle(it) }
    }

    /**
     * Updates vehicle information based on
     */
    fun updateVehicle(plateNumber: String, vehicleUpdate: Vehicle) {
        // Verify driver assigned to vehicle exists
        val dDriver = driverService.findByLicense(vehicleUpdate.driverLicense)
        val dVehicle = findVehicleByPlate(plateNumber)
        dVehicle.driver = dDriver
        dVehicle.plateNumber = vehicleUpdate.plateNumber
        dVehicle.make = vehicleUpdate.make
        dVehicle.model = vehicleUpdate.model
        dVehicle.telematics = vehicleUpdate.telematics
        dVehicle.year = vehicleUpdate.year

        dVehicle.save()
    }

    /**
     * Deletes vehicle entry by plate number
     */
    fun delete(plateNumber: String) {
        findVehicleByPlate(plateNumber).delete()
    }
}
