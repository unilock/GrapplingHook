package io.github.mg138.grapplinghook

import io.github.mg138.grapplinghook.item.GrapplingHook
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Main : ModInitializer {
    const val modId = "grappling_hook"
    val logger: Logger = LogManager.getLogger(modId)

    override fun onInitialize() {
        GrapplingHook.register()

        logger.info("Registered Grappling Hook.")
    }
}
