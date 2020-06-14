package io.jlite.example.vehicle.domain

import io.jlite.example.vehicle.domain.query.QDVehicle
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DVehicleTest {
    @Test
    fun insertFindDelete() {
        // Create new driver
        val driver = DDriver("Prince Charles", "HELLOTHERE")
        driver.save()

        // Create vehicle with driver
        val plateNumber = "KEK828"
        val vehicle = DVehicle(driver, plateNumber, "Mazda", "Axela", "2016", VehicleTelematics())
        vehicle.save()

        // Find by name
        val foundVehicle = QDVehicle()
            .plateNumber.eq(plateNumber)
            .findOne()

        // Test retrieved is the same
        assertEquals(foundVehicle, vehicle)

        // Delete
        vehicle.delete();
    }
}