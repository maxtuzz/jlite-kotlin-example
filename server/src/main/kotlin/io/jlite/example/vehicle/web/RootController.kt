package io.jlite.example.vehicle.web

import io.dinject.controller.Controller
import io.dinject.controller.Get
import io.dinject.controller.Path
import io.javalin.http.Context
import io.jlite.example.vehicle.api.HalBuilder
import io.jlite.example.vehicle.api.LinkResource
import io.jlite.example.vehicle.api.Links

@Controller
@Path("/")
class RootController {
    /**
     * Get meta returns a simple hateoas meta payload for initial navigation of the api
     */
    @Get
    fun getMeta(ctx: Context): LinkResource {
        val links = Links()

        links.addAll {
            HalBuilder(ctx)
                .toContextPath("self")
                .toVehicleBase()
                .toDriverBase()
                .build()
        }

        return LinkResource(links)
    }
}