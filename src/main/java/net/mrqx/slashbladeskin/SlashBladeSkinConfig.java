package net.mrqx.slashbladeskin;

import net.minecraftforge.common.ForgeConfigSpec;

public class SlashBladeSkinConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.IntValue SKIN_EXP_COST;
    public static final ForgeConfigSpec.IntValue SKIN_SOUL_COST;

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        commonBuilder.comment("SlashBlade Skin settings").push("common");

        SKIN_EXP_COST = commonBuilder
                .comment("Set the experience level consumption for obtaining SlashBlade Skin.(default: 10)")
                .defineInRange("skin_exp_cost", 10, 0, Integer.MAX_VALUE);

        SKIN_SOUL_COST = commonBuilder
                .comment("Set the proud soul consumption for obtaining SlashBlade Skin.(default: 1000)")
                .defineInRange("skin_soul_cost", 1000, 0, Integer.MAX_VALUE);

        commonBuilder.pop();
        COMMON_CONFIG = commonBuilder.build();
    }

}
