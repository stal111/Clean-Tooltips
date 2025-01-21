package net.mandalacreations.clean_tooltips.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import kotlin.Unit;
import net.mandalacreations.clean_tooltips.tooltip.TooltipFactory;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamaged()Z"), method = "getTooltipLines")
    private boolean cleanTooltips_getTooltipLines$cancelDurabilityTooltip(boolean original) {
        return false;
    }

    @Inject(at = @At("RETURN"), method = "addToTooltip")
    private <T extends TooltipProvider> void cleanTooltips_getTooltipLines$addDurabilityTooltip(DataComponentType<T> component, Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (component == DataComponents.LORE) {
            TooltipFactory.createDurability((ItemStack) (Object) this, consumer -> {
                tooltipAdder.accept(consumer);

                return Unit.INSTANCE;
            });
        }
    }
}
