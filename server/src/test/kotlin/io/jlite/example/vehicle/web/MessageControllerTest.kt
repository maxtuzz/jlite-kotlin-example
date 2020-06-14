package io.jlite.example.vehicle.web

import io.jlite.example.vehicle.ResourceHelp.Companion.read
import io.jlite.example.vehicle.domain.DDriver
import io.jlite.example.vehicle.domain.DMessage
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.hasItems
import org.junit.jupiter.api.Test

class MessageControllerTest : WebTest() {
    private val dDriver = DDriver("Max", "Tuzzolino")
    private val dMessage1 = DMessage(dDriver, "I am leaving")
    private val dMessage2 = DMessage(dDriver, "I have arrived")
    private val messageUrl = "$driverUrl/Tuzzolino/messages"

    @Test
    fun `GET messages for driver`() {
        dDriver.save()
        dMessage1.save()
        dMessage2.save()

        When {
            get(messageUrl)
        } Then {
            statusCode(200)
            body("content.messageContent", hasItems("I am leaving", "I have arrived"))
        }
    }

    @Test
    fun `POST new message for driver and GET`() {
        dDriver.save()

        Given {
            body(read("/request/message1.json"))
        } When {
            post(messageUrl)
        } Then {
            statusCode(201)
        }

        When {
            get(messageUrl)
        } Then {
            statusCode(200)
            body("content.messageContent", hasItems("Payload delivered successfully"))
        }
    }
}