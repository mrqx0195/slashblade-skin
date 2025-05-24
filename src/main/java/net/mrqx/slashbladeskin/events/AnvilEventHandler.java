package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrqx.slashbladeskin.SlashBladeSkinConfig;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;

@Mod.EventBusSubscriber
public class AnvilEventHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onAnvilUpdateEvent(AnvilUpdateEvent event) {
        ItemStack base = event.getLeft();
        ItemStack material = event.getRight();

        if (base.getItem() instanceof ItemSlashBlade && material.getItem() instanceof ItemSheathDesignDrawing) {
            ItemStack result = base.copy();
            CompoundTag skin = ItemSheathDesignDrawing.getBladeSkinFromItem(material);
            if (skin == null) {
                return;
            }
            result.getOrCreateTag().put(ItemSheathDesignDrawing.BLADE_SKIN_KEY, skin);
            ItemSheathDesignDrawing.updateBladeSkin(result);
            event.setOutput(result);
            event.setMaterialCost(1);
            event.setCost(SlashBladeSkinConfig.SKIN_EXP_COST.get());
            result.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> state.setProudSoulCount(Math.max(0, state.getProudSoulCount() - SlashBladeSkinConfig.SKIN_SOUL_COST.get())));
        }
    }
}
