package io.jlite.example.vehicle.web

import io.jlite.example.vehicle.ResourceHelp.Companion.read
import io.jlite.example.vehicle.domain.DDriver
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

class DriverControllerTest : WebTest() {
    @Test
    fun `GET all drivers`() {
        // Create driver
        DDriver("Driver1", "LEK131").save()
        DDriver("Driver2", "LEK132").save()

        When {
            get(driverUrl)
        } Then {
            statusCode(200)
            body("content.name", hasItems("Driver1", "Driver2"))
        }
    }

    @Test
    fun `GET driver`() {
        // Create driver
        val licenseNumber = "computer"
        val name = "Quantum"

        DDriver(name, licenseNumber).save()
        testGetByLicense(licenseNumber, name)
    }


    @Test
    fun `POST new driver and GET`() {
        // Get json payload
        val payload = read("/request/driver1a.json")

        Given {
            body(payload)
        } When {
            post(driverUrl)
        } Then {
            statusCode(201)
        }

        // Test that we can fetch from API
        testGetByLicense("ROOF_PIZZA", "Walter White")
    }

    @Test
    fun `PUT driver and GET`() {
        // Create dummy entry to update
        val licenseNumber = "ROOF_PIZZA"
        DDriver("Walter White", licenseNumber).save()

        // Get update payload
        val payload = read("/request/driver1b.json")

        Given {
            body(payload)
        } When {
            put("${driverUrl}/${licenseNumber}")
        } Then {
            statusCode(204)
        }

        testGetByLicense("DRUG_LORD", "Walter White")
    }

    @Test
    fun `DELETE driver and confirm`() {
        val licenseNumber = "MustafarSystem"
        val name = "DarthVader"
        DDriver(name, licenseNumber).save()

        val driverUrl = "${driverUrl}/${licenseNumber}"

        When {
            delete(driverUrl)
        } Then {
            statusCode(204)
        }

        // Now it shouldn't exist
        When {
            get(driverUrl)
        } Then {
            statusCode(404)
        }
    }

    /**
     * Returns driver by license
     */
    private fun testGetByLicense(licenseNumber: String, name: String) {
        When {
            get("${driverUrl}/${licenseNumber}")
        } Then {
            statusCode(200)
            body("name", Matchers.equalTo(name))
            body("licenseNumber", Matchers.equalTo(licenseNumber))
        }
    }
}