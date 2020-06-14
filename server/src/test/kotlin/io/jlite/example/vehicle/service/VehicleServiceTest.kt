package io.jlite.example.vehicle.service

import io.javalin.http.NotFoundResponse
import io.jlite.example.vehicle.api.Vehicle
import io.jlite.example.vehicle.domain.DDriver
import io.jlite.example.vehicle.domain.VehicleTelematics
import io.jlite.example.vehicle.domain.query.QDDriver
import io.jlite.example.vehicle.domain.query.QDMessage
import io.jlite.example.vehicle.domain.query.QDVehicle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import kotlin.random.Random
import kotlin.test.assertFailsWith

class VehicleServiceTest {
    private var vehicleService: VehicleService = VehicleService(
        DriverService()
    )
    private var driver = DDriver("TruckDriver Max", "TESLA")
    private val dummyPlate = "KEK989"

    @BeforeEach
    fun setup() {
        // Delete all vehicles first
        QDMessage().delete()
        QDVehicle().delete()
        QDDriver().delete()

        // Save our driver
        driver.save()

        // Set up new vehicle
        vehicleService.createVehicle(
            Vehicle(
                driver.licenseNumber,
                dummyPlate,
                "Tesla",
                "Semi",
                "2020",
                VehicleTelematics()
            )
        )
    }

    @Test
    fun `Create and fetch vehicle`() {
        val plateNumber = "LYK781"
        vehicleService.createVehicle(
            Vehicle(
                driver.licenseNumber,
                plateNumber,
                "Hello",
                "There",
                "3031",
                VehicleTelematics()
            )
        )

        val vehicle = vehicleService.getVehicle(plateNumber)

        assertThat(vehicle.plateNumber).isEqualTo(plateNumber)
        assertThat(vehicle.driverLicense).isEqualTo(driver.licenseNumber)
    }

    @Test
    fun `Fetch all vehicles`() {
        val plateNumber = "LYK781"
        vehicleService.createVehicle(
            Vehicle(
                driver.licenseNumber,
                plateNumber,
                "Hello",
                "There",
                "3031",
                VehicleTelematics()
            )
        )
        vehicleService.createVehicle(
            Vehicle(
                driver.licenseNumber,
                plateNumber + Random(1),
                "Rick", "There", "3031",
                VehicleTelematics()
            )
        )

        val vehicles = vehicleService.getVehicles()

        val map = vehicles.map { it.make }
        Assertions.assertAll(
            Executable { assertThat(map).contains("Hello") },
            Executable { assertThat(map).contains("Rick") }
        )
    }

    @Test
    fun `Update vehicle details`() {
        vehicleService.updateVehicle(
            dummyPlate,
            Vehicle(driver.licenseNumber, dummyPlate, "Make", "Model", "Year", VehicleTelematics())
        )

        val vehicle = vehicleService.getVehicle(dummyPlate)

        assertThat(vehicle.make).isEqualTo("Make")
    }

    @Test
    fun `Delete vehicle`() {
        vehicleService.delete(dummyPlate)

        assertFailsWith<NotFoundResponse> {
            vehicleService.getVehicle(dummyPlate)
        }
    }
}