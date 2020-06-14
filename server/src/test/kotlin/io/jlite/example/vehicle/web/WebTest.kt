package io.jlite.example.vehicle.web

import io.javalin.Javalin
import io.jlite.example.vehicle.domain.query.QDDriver
import io.jlite.example.vehicle.domain.query.QDMessage
import io.jlite.example.vehicle.domain.query.QDVehicle
import io.jlite.example.vehicle.startServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

open class WebTest {
    @BeforeEach
    fun tearDown() {
        QDMessage().delete()
        QDVehicle().delete()
        QDDriver().delete()
    }

    companion object {
        lateinit var app: Javalin
        private const val servicePort: Int = 9091
        private const val baseUrl = "http://localhost:$servicePort"
        const val driverUrl = "$baseUrl/drivers"
        const val vehicleUrl = "$baseUrl/vehicles"

        @BeforeAll
        @JvmStatic
        fun setup() {
            println("Starting server on port $servicePort")
            app = startServer(servicePort)

            Thread.sleep(1000)
        }
        @AfterAll
        @JvmStatic
        fun stopServer() {
            Thread.sleep(1000)
            app.stop()
        }
    }
}
