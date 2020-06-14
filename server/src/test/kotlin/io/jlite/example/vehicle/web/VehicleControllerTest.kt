package io.jlite.example.vehicle.web

import io.jlite.example.vehicle.ResourceHelp.Companion.read
import io.jlite.example.vehicle.domain.DDriver
import io.jlite.example.vehicle.domain.DVehicle
import io.jlite.example.vehicle.domain.VehicleTelematics
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

class VehicleControllerTest : WebTest() {
    private val dDriver = DDriver("Max", "Tuzzolino")
    private val plateNumber = "LKY787"
    private val dVehicle = DVehicle(dDriver, plateNumber, "Tesla", "Semi", "2020", VehicleTelematics())
    private val vehicleRequestUrl = "$vehicleUrl/${plateNumber}"

    @Test
    fun `GET all vehicles`() {
        dDriver.save()
        dVehicle.save()

        val dDriver1 = DDriver("Hello", "There")
        dDriver1.save()
        DVehicle(dDriver1, "LKYaaa", "Toyota", "Highlander", "2020", VehicleTelematics()).save()

        When {
            get(vehicleUrl)
        } Then {
            statusCode(200)
            body("content.model", hasItems("Semi", "Highlander"))
        }
    }

    @Test
    fun `GET vehicle`() {
        dDriver.save()
        dVehicle.save()

        When {
            get(vehicleRequestUrl)
        } Then {
            statusCode(200)
            body("make", Matchers.equalTo("Tesla"))
            body("model", Matchers.equalTo("Semi"))
        }
    }

    @Test
    fun `POST and GET vehicle`() {
        dDriver.save()
        val payload = read("/request/vehicle1a.json")

        Given {
            body(payload)
        } When {
            post(vehicleUrl)
        } Then {
            statusCode(201)
        }

        When {
            get("$vehicleUrl/KJYYKL")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `PUT vehicle and GET updated vehicle`() {
        dDriver.save()
        dVehicle.save()
        val payload = read("/request/vehicle2.json")

        Given {
            body(payload)
            log().all()
        } When {
            put(vehicleRequestUrl)
        } Then {
            statusCode(204)
        }

        When {
            get("$vehicleUrl/UPDATED_PLATE")
        } Then {
            statusCode(200)
            body("plateNumber", Matchers.equalTo("UPDATED_PLATE"))
        }
    }

    @Test
    fun `DELETE vehicle and GET confirm status not found`() {
        dDriver.save()
        dVehicle.save()

        When {
            delete(vehicleRequestUrl)
        } Then {
            statusCode(204)
        }

        When {
            get(vehicleRequestUrl)
        } Then {
            statusCode(404)
        }
    }
}