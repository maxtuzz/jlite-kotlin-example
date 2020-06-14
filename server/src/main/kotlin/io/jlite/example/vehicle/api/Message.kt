package io.jlite.example.vehicle.api

import io.javalin.http.Context

open class Message(val messageContent: String): HalResource<Message>() {
    override fun withHal(ctx: Context): Message {
        TODO("Not yet implemented")
    }
}