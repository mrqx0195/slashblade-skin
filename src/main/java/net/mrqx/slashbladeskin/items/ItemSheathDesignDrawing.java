package net.mrqx.slashbladeskin.items;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.init.DefaultResources;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.mrqx.slashbladeskin.SlashBladeSkin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ItemSheathDesignDrawing extends Item {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM,
            SlashBladeSkin.MODID);
    public static final Supplier<Item> BLADE_MODEL_PAPER = ITEMS.register("sheath_design_drawing", ItemSheathDesignDrawing::new);

    public static final String BLADE_SKIN_KEY = "SlashBladeSkin";
    public static final String BLADE_MODEL_KEY = "ModelName";
    public static final String BLADE_TEXTURE_KEY = "TextureName";
    public static final String BLADE_LANG_KEY = "translationKey";
    public static final String DEFAULT_BLADE_LANG_KEY = "item.slashblade.slashblade";

    public static final CompoundTag DEFAULT_BLADE_SKIN = new CompoundTag();

    static {
        DEFAULT_BLADE_SKIN.putString(BLADE_MODEL_KEY, DefaultResources.resourceDefaultModel.toString());
        DEFAULT_BLADE_SKIN.putString(BLADE_TEXTURE_KEY, DefaultResources.resourceDefaultTexture.toString());
        DEFAULT_BLADE_SKIN.putString(BLADE_LANG_KEY, DEFAULT_BLADE_LANG_KEY);
    }

    public ItemSheathDesignDrawing() {
        super(new Properties().rarity(Rarity.RARE));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        appendSkinText(stack, tooltip);
    }

    public static void updateBladeSkin(ItemStack itemStack) {
        itemStack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            CompoundTag skin = getBladeSkinFromItem(itemStack);
            if (skin == null) {
                return;
            }
            state.setModel(new ResourceLocation(skin.getString(BLADE_MODEL_KEY)));
            state.setTexture(new ResourceLocation(skin.getString(BLADE_TEXTURE_KEY)));
        });
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null || compoundTag.isEmpty() || !compoundTag.contains(BLADE_SKIN_KEY, CompoundTag.TAG_COMPOUND)) {
            return super.isFoil(itemStack);
        }
        return true;
    }

    public static CompoundTag getBladeSkinFromBlade(ItemStack itemStack) {
        CompoundTag compoundTag = new CompoundTag();
        itemStack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> compoundTag.merge(getBladeSkinFromState(state)));
        return compoundTag;
    }

    public static CompoundTag getBladeSkinFromState(ISlashBladeState state) {
        CompoundTag compoundTag = new CompoundTag();
        state.getModel().ifPresent(res -> compoundTag.putString(BLADE_MODEL_KEY, res.toString()));
        state.getTexture().ifPresent(res -> compoundTag.putString(BLADE_TEXTURE_KEY, res.toString()));
        compoundTag.putString(BLADE_LANG_KEY, state.getTranslationKey());
        return compoundTag;
    }

    public static CompoundTag getBladeSkinFromResourceLocation(ResourceLocation model, ResourceLocation texture, String translationKey) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(BLADE_MODEL_KEY, model.toString());
        compoundTag.putString(BLADE_TEXTURE_KEY, texture.toString());
        compoundTag.putString(BLADE_LANG_KEY, translationKey);
        return compoundTag;
    }

    public static CompoundTag getBladeSkinFromItem(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null || compoundTag.isEmpty() || !compoundTag.contains(BLADE_SKIN_KEY, CompoundTag.TAG_COMPOUND)) {
            return null;
        }
        return compoundTag.getCompound(BLADE_SKIN_KEY);
    }

    public static void appendSkinText(ItemStack itemStack, List<Component> tooltip) {
        CompoundTag compoundTag = getBladeSkinFromItem(itemStack);
        if (compoundTag != null && compoundTag.contains(BLADE_LANG_KEY, CompoundTag.TAG_STRING)) {
            tooltip.add(Component.translatable("tooltips.slashbladeskin.sheath_design_drawing", Component.translatable(compoundTag.getString(BLADE_LANG_KEY))).withStyle(ChatFormatting.GREEN));
        }
    }
}
