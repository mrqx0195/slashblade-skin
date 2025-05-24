package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.SlashBladeCreativeGroup;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;

public class CreativeTabEventHandler {
    public static void onBuildCreativeModeTabContentsEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == SlashBladeCreativeGroup.SLASHBLADE_GROUP.getKey()) {
            event.accept(ItemSheathDesignDrawing.BLADE_MODEL_PAPER);
        }
    }
}
