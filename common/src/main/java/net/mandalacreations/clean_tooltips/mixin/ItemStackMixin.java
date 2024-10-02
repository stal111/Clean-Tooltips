package net.mandalacreations.clean_tooltips.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author stal111
 * @since 2023-04-19
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamaged()Z"), method = "getTooltipLines")
    private boolean cleanTooltips_getTooltipLines$cancelDurabilityTooltip(boolean original) {
        return false;
    }
}
