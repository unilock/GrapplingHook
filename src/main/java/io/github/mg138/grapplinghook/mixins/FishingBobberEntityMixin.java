package io.github.mg138.grapplinghook.mixins;

import io.github.mg138.grapplinghook.item.GrapplingHook;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Inject(
            at = @At("HEAD"),
            method = "removeIfInvalid(Lnet/minecraft/entity/player/PlayerEntity;)Z",
            cancellable = true
    )
    private void grappling_hook_removeInvalid(@NotNull PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        FishingBobberEntity entity = (FishingBobberEntity) (Object) this;
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();

        boolean instanceCheck = itemStack.getItem() instanceof FishingRodItem || itemStack2.getItem() instanceof FishingRodItem;

        if (!player.isRemoved() && player.isAlive() && instanceCheck && !(entity.squaredDistanceTo(player) > 1024.0D)) {
            cir.setReturnValue(false);
        } else {
            entity.discard();
            cir.setReturnValue(true);
        }
    }
}
