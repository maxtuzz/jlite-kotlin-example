package io.jlite.example.vehicle.service

import io.javalin.http.NotFoundResponse
import io.jlite.example.vehicle.api.Driver
import io.jlite.example.vehicle.domain.query.QDDriver
import io.jlite.example.vehicle.domain.query.QDMessage
import io.jlite.example.vehicle.domain.query.QDVehicle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class DriverServiceTest {
    private var driverService: DriverService = DriverService()
    private val license = "TEST_LICENSE"

    @BeforeEach
    fun createDummy() {
        QDMessage().delete()
        QDVehicle().delete()
        QDDriver().delete()
        driverService.createDriver("Hello", license)
    }

    @Test
    fun `Fetch all drivers`() {
        driverService.createDriver("test name", "test licence")
        val drivers = driverService.getDrivers()

        assertThat(drivers).hasSize(2)
    }

    @Test
    fun `Create and fetch driver`() {
        val name = "Max Tuzzolino"
        val licenseNumber = "KENOBI"

        driverService.createDriver(name, licenseNumber)

        val driver = driverService.getDriver(licenseNumber)
        assertThat(driver.name).isEqualTo(name)
    }

    @Test
    fun `Create and update driver details`() {
        val updatedLicenseNumber = "Hello-There"
        driverService.updateDriver("TEST_LICENSE", Driver("Kenobi", updatedLicenseNumber))

        val driver = driverService.findByLicense(updatedLicenseNumber)

        Assertions.assertAll(
            Executable { assertThat(driver).isNotNull },
            Executable { assertThat(driver.licenseNumber).isEqualTo(updatedLicenseNumber) }
        )
    }

    @Test
    fun `Delete driver`() {
        val dDriver = driverService.findByLicense(license)

        assertNotNull(dDriver)

        driverService.deleteDriver(license)

        assertFailsWith<NotFoundResponse> {
            driverService.findByLicense(license)
        }
    }
}