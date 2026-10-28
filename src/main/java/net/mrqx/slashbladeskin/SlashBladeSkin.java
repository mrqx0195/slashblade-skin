package net.mrqx.slashbladeskin;

import com.mojang.logging.LogUtils;
import net.mrqx.slashbladeskin.events.CreativeTabEventHandler;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(SlashBladeSkin.MODID)
public class SlashBladeSkin {
    public static final String MODID = "slashbladeskin";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public SlashBladeSkin(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(CreativeTabEventHandler::onBuildCreativeModeTabContentsEvent);
        
        ItemSheathDesignDrawing.register(modEventBus);
        
        modContainer.registerConfig(ModConfig.Type.COMMON, SlashBladeSkinConfig.COMMON_CONFIG);
    }
}
