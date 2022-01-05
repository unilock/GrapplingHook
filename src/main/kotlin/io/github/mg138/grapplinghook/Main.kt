package io.github.mg138.grapplinghook

import eu.pb4.polymer.api.resourcepack.PolymerRPUtils
import io.github.mg138.grapplinghook.item.GrapplingHook
import net.fabricmc.api.DedicatedServerModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object Main : DedicatedServerModInitializer {
    const val modId = "grappling_hook"
    val logger: Logger = LogManager.getLogger(modId)

    override fun onInitializeServer() {
        PolymerRPUtils.addAssetSource(modId)

        GrapplingHook.register()

        logger.info("Registered Grappling hook.")
    }
}