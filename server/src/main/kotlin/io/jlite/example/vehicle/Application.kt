package io.jlite.example.vehicle

import io.dinject.SystemContext
import io.dinject.controller.WebRoutes
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.http.staticfiles.Location
import io.javalin.plugin.json.JavalinJackson
import io.jlite.example.vehicle.config.AppGlobal
import io.jlite.example.vehicle.config.ObjectMapperConfig

fun main() {
    startServer(AppGlobal.port())
}

fun startServer(port: Int): Javalin {
    return create(SystemContext.getBeans(WebRoutes::class.java)).start(port)
}

/**
 * Creates javalin server with list of routes
 */
fun create(routes: List<WebRoutes>): Javalin {
    val app = Javalin.create() { config ->
        config.enableCorsForAllOrigins()
        config.showJavalinBanner = false
        config.logIfServerNotStarted = true

        // Serve static files when running in prod mode (quick way of serving static files)
        if (System.getenv("PROD_MODE") == "true") {
            config.addStaticFiles("/app/public", Location.EXTERNAL)
        }
    }

    // Use custom json serializer
    JavalinJackson.configure(ObjectMapperConfig.get())

    return app.routes { routes.forEach(WebRoutes::registerRoutes) }
}
