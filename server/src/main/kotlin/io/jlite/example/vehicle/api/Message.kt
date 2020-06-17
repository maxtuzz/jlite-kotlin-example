package io.jlite.example.vehicle.api

import io.javalin.http.Context

open class Message(val messageContent: String, var licenseNumber: String? = null) : HalResource<Message>() {
    override fun withHal(ctx: Context): Message {
        links.addAll {
            HalBuilder(ctx)
                .toDriver("driver", licenseNumber!!)
                .toMessages("self", licenseNumber!!)
                .build()
        }

        return this
    }
}