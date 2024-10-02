package net.mandalacreations.clean_tooltips.neoforge.mixin;

import net.mandalacreations.clean_tooltips.client.DurabilitySection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/util/AttributeUtil;addAttributeTooltips(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;Lnet/neoforged/neoforge/common/util/AttributeTooltipContext;)V"), method = "getTooltipLines")
    private Consumer<Component> cleanTooltips_getTooltipLines$addDurabilityTooltip(Consumer<Component> consumer) {
        DurabilitySection.create(consumer, (ItemStack) (Object) this);

        return consumer;
    }
}
