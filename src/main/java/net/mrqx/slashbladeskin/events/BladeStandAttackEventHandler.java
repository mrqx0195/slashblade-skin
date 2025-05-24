package net.mrqx.slashbladeskin.events;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.entity.BladeStandEntity;
import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrqx.slashbladeskin.SlashBladeSkinConfig;
import net.mrqx.slashbladeskin.items.ItemSheathDesignDrawing;

@Mod.EventBusSubscriber
public class BladeStandAttackEventHandler {
    @SubscribeEvent
    public static void onBladeStandAttackEvent(SlashBladeEvent.BladeStandAttackEvent event) {
        if (event.getDamageSource().getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack blade = event.getBlade();
            Level world = player.level();
            ISlashBladeState state = event.getSlashBladeState();
            BladeStandEntity bladeStand = event.getBladeStand();
            if (blade.isEmpty() || !(stack.is(Items.PAPER))) {
                return;
            }
            if (state.getProudSoulCount() >= SlashBladeSkinConfig.SKIN_SOUL_COST.get()) {
                state.setProudSoulCount(Math.max(0, state.getProudSoulCount() - SlashBladeSkinConfig.SKIN_SOUL_COST.get()));

                ItemStack itemStack = new ItemStack(ItemSheathDesignDrawing.BLADE_MODEL_PAPER.get());
                CompoundTag compoundTag = ItemSheathDesignDrawing.getBladeSkinFromState(state);
                itemStack.getOrCreateTag().put(ItemSheathDesignDrawing.BLADE_SKIN_KEY, compoundTag);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                RandomSource random = player.getRandom();
                world.playSound(bladeStand, bladeStand.getPos(), SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);

                for (int i = 0; i < 32 && !player.level().isClientSide(); ++i) {
                    double xDist = (random.nextFloat() * 2.0F - 1.0F);
                    double yDist = (random.nextFloat() * 2.0F - 1.0F);
                    double zDist = (random.nextFloat() * 2.0F - 1.0F);
                    if (xDist * xDist + yDist * yDist + zDist * zDist <= 1.0F) {
                        double x = bladeStand.getX(xDist / 4.0F);
                        double y = bladeStand.getY(0.5F + yDist / 4.0F);
                        double z = bladeStand.getZ(zDist / 4.0F);
                        ((ServerLevel) world).sendParticles(ParticleTypes.PORTAL, x, y, z, 0, xDist, yDist + 0.2, zDist, 1.0F);
                    }
                }

                player.drop(itemStack, true);

                event.setCanceled(true);
            }
        }
    }
}
