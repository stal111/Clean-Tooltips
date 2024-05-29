package net.mandalacreations.clean_tooltips.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.mandalacreations.clean_tooltips.client.DurabilitySection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2023-04-19
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addAttributeTooltips(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;)V"), method = "getTooltipLines")
    private Consumer<Component> cleanTooltips_getTooltipLines$addDurabilityTooltip(Consumer<Component> consumer) {
        DurabilitySection.create(consumer, (ItemStack) (Object) this);

        return consumer;
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamaged()Z"), method = "getTooltipLines")
    private boolean cleanTooltips_getTooltipLines$cancelDurabilityTooltip(boolean original) {
        return false;
    }
}
