package io.jlite.example.vehicle.service

import io.jlite.example.vehicle.api.Message
import io.jlite.example.vehicle.domain.DMessage
import io.jlite.example.vehicle.domain.query.QDMessage
import javax.inject.Singleton

@Singleton
class MessageService(private val driverService: DriverService) {
    /**
     * Saves a message sent from specific driver
     */
    fun save(driverLicense: String, message: Message) {
        val dDriver = driverService.findByLicense(driverLicense)
        DMessage(dDriver, message.messageContent).save()
    }

    /**
     * Return all messages for a driver
     */
    fun getMessages(driverLicenseNumber: String): List<Message> {
        return QDMessage()
            .driver.licenseNumber.eq(driverLicenseNumber)
            .findList()
            .map { Message(it.messageContent) }
    }
}