package io.github.mg138.grapplinghook.item

import eu.pb4.polymer.core.api.item.PolymerItem
import io.github.mg138.grapplinghook.Main
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FishingBobberEntity
import net.minecraft.item.*
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object GrapplingHook : FishingRodItem(
    FabricItemSettings()
        .maxCount(1)
        .rarity(Rarity.RARE)
), PolymerItem {
    override fun getPolymerItem(itemStack: ItemStack?, player: ServerPlayerEntity?): Item = Items.FISHING_ROD

    override fun isDamageable() = false

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        val hook = user.fishHook

        if (hook != null) {
            if (!world.isClient) {
                val v = user.velocity.add(hook.pos.subtract(user.pos).normalize().multiply(3.0))

                user.velocity = v

                if (user is ServerPlayerEntity) {
                    user.networkHandler.sendPacket(EntityVelocityUpdateS2CPacket(user))
                }

                hook.discard()
            }

            world.playSound(
                null,
                user.x,
                user.y,
                user.z,
                SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE,
                SoundCategory.NEUTRAL,
                1.0f,
                0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
            )
        } else {
            if (!world.isClient) {
                world.spawnEntity(FishingBobberEntity(user, world, 0, 0).apply {
                    val v = user.velocity.normalize()
                    addVelocity(v.x, 0.0, v.z)
                })
            }

            world.playSound(
                null,
                user.x,
                user.y,
                user.z,
                SoundEvents.ENTITY_FISHING_BOBBER_THROW,
                SoundCategory.NEUTRAL,
                0.5f,
                0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
            )
        }

        return TypedActionResult.pass(itemStack)
    }

    fun register() {
        val grapplingHook = Registry.register(Registries.ITEM, Identifier(Main.modId, "grappling_hook"), this)

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register { entries ->
            entries.add(grapplingHook)
        }
    }
}
