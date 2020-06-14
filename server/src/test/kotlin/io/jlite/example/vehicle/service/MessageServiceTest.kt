package io.jlite.example.vehicle.service

import io.jlite.example.vehicle.api.Message
import io.jlite.example.vehicle.domain.DDriver
import io.jlite.example.vehicle.domain.query.QDDriver
import io.jlite.example.vehicle.domain.query.QDMessage
import io.jlite.example.vehicle.domain.query.QDVehicle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * You get the gist
 */
class MessageServiceTest {
    private val messageService = MessageService(DriverService())
    private val driverLicenseNumber = "MAXMAX"

    @BeforeEach
    fun setup() {
        QDMessage().delete()
        QDVehicle().delete()
        QDDriver().delete()
        DDriver("Max", driverLicenseNumber).save()
    }

    @Test
    fun `Create and fetch driver messages`() {
        val message = Message("Picked up cargo")
        messageService.save(driverLicenseNumber, message)
        messageService.save(driverLicenseNumber, message)

        val messages = messageService.getMessages(driverLicenseNumber)

        assertThat(messages).hasSize(2)
    }
}