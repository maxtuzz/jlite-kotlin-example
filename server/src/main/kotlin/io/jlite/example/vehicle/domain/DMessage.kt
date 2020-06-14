package io.jlite.example.vehicle.domain

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "message")
class DMessage(driver: DDriver, messageContent: String) : BaseDomain() {
    @ManyToOne
    var driver = driver
    var messageContent = messageContent
}