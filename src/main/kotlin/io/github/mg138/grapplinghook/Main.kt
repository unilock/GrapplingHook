package io.github.mg138.grapplinghook

import eu.pb4.polymer.api.resourcepack.PolymerRPUtils
import io.github.mg138.grapplinghook.item.GrapplingHook
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object Main : ModInitializer {
    const val modId = "grappling_hook"
    val logger: Logger = LogManager.getLogger(modId)

    override fun onInitialize() {
        PolymerRPUtils.addAssetSource(modId)

        GrapplingHook.register()

        logger.info("Registered Grappling hook.")
    }
}
