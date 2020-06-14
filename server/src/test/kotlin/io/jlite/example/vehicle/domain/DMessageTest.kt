package io.jlite.example.vehicle.domain

import io.jlite.example.vehicle.domain.query.QDMessage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class DMessageTest {
    @Test
    fun insertFindDelete() {
        // Create new driver
        val driver = DDriver("Max Tuzzolino", "hello_there")
        driver.save()

        val message1 = DMessage(driver, "Payload delivered successfully")
        message1.save()

        val message2 = DMessage(driver, "Picked up second payload")
        message2.save()

        val foundMessages = QDMessage()
            .driver.eq(driver)
            .findList()

        Assertions.assertAll(
            Executable { assertThat(foundMessages).hasSize(2) },
            Executable { assertThat(foundMessages.map { it.driver }).containsOnly(driver) }
        )

        QDMessage().delete()
    }
}