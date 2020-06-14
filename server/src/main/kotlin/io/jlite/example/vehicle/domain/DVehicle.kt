package io.jlite.example.vehicle.domain

import io.ebean.annotation.Cache
import io.ebean.annotation.DbJson
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Cache(nearCache = true, naturalKey = ["plateNumber"])
@Entity
@Table(name = "vehicle")
class DVehicle(
    driver: DDriver,
    plateNumber: String,
    make: String,
    model: String,
    year: String,
    telematics: VehicleTelematics
) : BaseDomain() {
    @ManyToOne
    var driver = driver

    @Column(unique = true)
    var plateNumber = plateNumber
    var make = make
    var model = model

    var year = year

    // Store as a document
    @DbJson
    var telematics = telematics
}