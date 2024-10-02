package net.mandalacreations.clean_tooltips.fabric.mixin;

import net.mandalacreations.clean_tooltips.client.DurabilitySection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addAttributeTooltips(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;)V"), method = "getTooltipLines")
    private Consumer<Component> cleanTooltips_getTooltipLines$addDurabilityTooltip(Consumer<Component> consumer) {
        DurabilitySection.create(consumer, (ItemStack) (Object) this);

        return consumer;
    }
}
