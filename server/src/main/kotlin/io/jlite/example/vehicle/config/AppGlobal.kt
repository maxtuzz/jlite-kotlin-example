package io.jlite.example.vehicle.config

import io.avaje.config.Config

class AppGlobal {
    companion object {
        fun port(): Int {
            return Config.getInt("app.port", 8090)
        }

        fun appName(): String {
            return Config.get("app.name", "vehicle-api")
        }

        /**
         * Returns the google k8 service name
         */
        fun kServiceName(): String? {
            return Config.get("app.k_service")
        }

        /**
         * Google k8 cloud run
         */
        fun isKService(): Boolean {
            return kServiceName() != null
        }

        /**
         * Google k8 app version
         */
        fun kRevision(): String? {
            return Config.get("app.k_revision")
        }
    }
}