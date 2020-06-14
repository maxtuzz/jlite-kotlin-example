package io.jlite.example.vehicle.api

import io.javalin.http.Context

interface Hal<T> {
    fun withHal(ctx: Context): T
}