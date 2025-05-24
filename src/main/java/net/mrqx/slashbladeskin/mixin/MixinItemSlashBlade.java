package net.mrqx.slashbladeskin.mixin;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemSlashBlade.class)
public abstract class MixinItemSlashBlade {
    @Inject(method = "lambda$appendHoverText$24(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;Lmods/flammpfeil/slashblade/capability/slashblade/ISlashBladeState;)V", at = @At(value = "INVOKE", target = "Lmods/flammpfeil/slashblade/item/ItemSlashBlade;appendSpecialEffects(Ljava/util/List;Lmods/flammpfeil/slashblade/capability/slashblade/ISlashBladeState;)V"))
    public void injectAppendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, ISlashBladeState s, CallbackInfo ci) {
        ItemSheathDesignDrawing.appendSkinText(stack, tooltip);
    }
}
