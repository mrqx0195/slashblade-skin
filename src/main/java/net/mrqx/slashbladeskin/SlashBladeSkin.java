package net.mrqx.slashbladeskin;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrqx.slashbladeskin.events.CreativeTabEventHandler;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;
import org.slf4j.Logger;

@Mod(SlashBladeSkin.MODID)
public class SlashBladeSkin {
    public static final String MODID = "slashbladeskin";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SlashBladeSkin() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(CreativeTabEventHandler::onBuildCreativeModeTabContentsEvent);

        ItemSheathDesignDrawing.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SlashBladeSkinConfig.COMMON_CONFIG);
    }
}
