package io.jlite.example.vehicle.domain

import io.ebean.annotation.Cache
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Cache(nearCache = true, naturalKey = ["licenseNumber"])
@Entity
@Table(name = "driver")
class DDriver(name: String, licenseNumber: String): BaseDomain() {
    @Column(unique = true, nullable = false, length = 45)
    var name = name
    @Column(unique = true, nullable = false, length = 45)
    var licenseNumber = licenseNumber
}
