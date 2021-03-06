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
    /**
     * Get driver messages.
     * Returns all messages driver has sent.
     */
    @Get
    fun getMessages(licenseNumber: String, ctx: Context): ListResponse<Message> {
        return ListResponse(messageService.getMessages(licenseNumber)
            .map { it.withHal(ctx) })
            .withHal(ctx)
    }

    /**
     * Create a new message.
     * Adds an additional message to drivers chat log.
     */
    @Post
    fun createMessage(licenseNumber: String, message: Message) {
        messageService.save(message, licenseNumber)
    }
}