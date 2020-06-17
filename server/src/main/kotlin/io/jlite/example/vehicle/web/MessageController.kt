package io.jlite.example.vehicle.web

import io.dinject.controller.Controller
import io.dinject.controller.Get
import io.dinject.controller.Path
import io.dinject.controller.Post
import io.javalin.http.Context
import io.jlite.example.vehicle.api.ListResponse
import io.jlite.example.vehicle.api.Message
import io.jlite.example.vehicle.service.MessageService

@Controller
@Path("/drivers/:licenseNumber/messages")
class MessageController(private val messageService: MessageService) {
    @Get
    fun getMessages(licenseNumber: String, ctx: Context): ListResponse<Message> {
        return ListResponse(messageService.getMessages(licenseNumber)).withHal(ctx)
    }

    @Post
    fun createMessage(licenseNumber: String, message: Message)  {
        messageService.save(message, licenseNumber)
    }
}