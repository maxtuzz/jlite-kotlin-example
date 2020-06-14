package io.jlite.example.vehicle.domain

import io.jlite.example.vehicle.domain.query.QDDriver
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class DDriverTest {
    @Test
    fun insertFindDelete() {
        // Create new driver
        val name = "Scooby Doo"
        val driver = DDriver(name, "hello_there_kenobi")
        driver.save()

        // Find by name
        val foundDriver = QDDriver()
            .name.eq(name)
            .findOne()

        // Test retrieved is the same
        assertEquals(foundDriver, driver)

        // Delete
        driver.delete()
    }
}