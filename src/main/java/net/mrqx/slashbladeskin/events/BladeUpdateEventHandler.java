package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class BladeUpdateEventHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onUpdateEvent(SlashBladeEvent.UpdateEvent event) {
        ItemSheathDesignDrawing.updateBladeSkin(event.getBlade());
    }
}
