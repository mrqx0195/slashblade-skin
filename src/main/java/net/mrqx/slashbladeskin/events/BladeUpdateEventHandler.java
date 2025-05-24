package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;

@Mod.EventBusSubscriber
public class BladeUpdateEventHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onUpdateEvent(SlashBladeEvent.UpdateEvent event) {
        ItemSheathDesignDrawing.updateBladeSkin(event.getBlade());
    }
}
