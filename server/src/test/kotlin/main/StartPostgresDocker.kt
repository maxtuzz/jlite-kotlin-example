package main

import io.ebean.docker.commands.PostgresConfig
import io.ebean.docker.commands.PostgresContainer

/**
 * This will create postgres instance in docker used for local development
 *
 * Useful for automating project setup
 */
fun main() {
    val config = PostgresConfig("12")
    config.setContainerName("jlite-pg12")
    config.setUser("vehicle")
    config.password = "test"
    config.dbName = "vehicle"
    config.port = 9432

    val container = PostgresContainer(config)
    container.start()

    // Print output
    println("url: ${container.jdbcUrl()}")
    println("user: ${config.username}")
    println("password: ${config.password}")
}