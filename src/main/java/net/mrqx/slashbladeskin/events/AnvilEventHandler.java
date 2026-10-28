package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.capability.slashblade.BladeStateAccess;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.mrqx.slashbladeskin.SlashBladeSkinConfig;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber
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
            CompoundTag customData = result.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            customData.put(ItemSheathDesignDrawing.BLADE_SKIN_KEY, skin);
            result.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
            
            ItemSheathDesignDrawing.updateBladeSkin(result);
            event.setOutput(result);
            event.setMaterialCost(1);
            event.setCost(SlashBladeSkinConfig.SKIN_EXP_COST.get());
            BladeStateAccess.of(result).ifPresent(state -> state.setProudSoulCount(Math.max(0, state.getProudSoulCount() - SlashBladeSkinConfig.SKIN_SOUL_COST.get())));
        }
    }
}
