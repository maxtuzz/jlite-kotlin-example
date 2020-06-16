package io.jlite.example.vehicle.service

import io.javalin.http.NotFoundResponse
import io.jlite.example.vehicle.api.Driver
import io.jlite.example.vehicle.domain.DDriver
import io.jlite.example.vehicle.domain.query.QDDriver
import javax.inject.Singleton

@Singleton
class DriverService {
    fun createDriver(name: String, license: String) {
        DDriver(name, license).save()
    }

    fun findByLicense(licenseNumber: String): DDriver {
        return QDDriver().licenseNumber.eq(licenseNumber).findOne()
            ?: throw NotFoundResponse("A driver with that license was not found")
    }

    fun getDriver(licenseNumber: String): Driver {
        val dDriver = findByLicense(licenseNumber)
        return Driver(dDriver.name, dDriver.licenseNumber)
    }

    fun updateDriver(licenseNumber: String, updatedDriver: Driver) {
        val dDriver = findByLicense(licenseNumber)
        dDriver.name = updatedDriver.name
        dDriver.licenseNumber = updatedDriver.licenseNumber

        dDriver.save()
    }

    fun deleteDriver(license: String) {
        QDDriver()
            .licenseNumber.eq(license)
            .delete()
    }

    fun getDrivers(): List<Driver> {
        return QDDriver().findList().map { Driver(it.name, it.licenseNumber) }
    }
}
