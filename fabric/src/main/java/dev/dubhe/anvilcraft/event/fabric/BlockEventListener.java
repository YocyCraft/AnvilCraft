package dev.dubhe.anvilcraft.event.fabric;

import dev.dubhe.anvilcraft.api.hammer.IHammerChangeable;
import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import dev.dubhe.anvilcraft.client.gui.screen.inventory.AnvilHammerScreen;
import dev.dubhe.anvilcraft.item.AnvilHammerItem;
import dev.dubhe.anvilcraft.network.HammerUsePack;
import dev.dubhe.anvilcraft.util.StateUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockEventListener {
    /**
     * 初始化
     */
    public static void init() {
        AttackBlockCallback.EVENT.register(BlockEventListener::anvilHammerAttack);
        UseBlockCallback.EVENT.register(BlockEventListener::anvilHammerUse);
    }

    private static InteractionResult anvilHammerAttack(
        @NotNull Player player, Level level, InteractionHand hand, BlockPos blockPos, Direction direction
    ) {
        if (player.getItemInHand(hand).getItem() instanceof AnvilHammerItem) {
            AnvilHammerItem.attackBlock(player, blockPos, level);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult anvilHammerUse(
        @NotNull Player player, @NotNull Level level, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (player.getItemInHand(hand).getItem() instanceof AnvilHammerItem) {
            BlockPos pos = hitResult.getBlockPos();
            if (AnvilHammerItem.ableToUseAnvilHammer(level, pos, player)) {
                Block b = level.getBlockState(pos).getBlock();
                if (b instanceof IHammerRemovable
                    && !(b instanceof IHammerChangeable)
                    && !player.isShiftKeyDown()
                ) {
                    return InteractionResult.PASS;
                }
                if (level.isClientSide()) {
                    clientHandler(player, level, hand, hitResult, pos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Environment(EnvType.CLIENT)
    private static void clientHandler(
        @NotNull Player player,
        @NotNull Level level,
        InteractionHand hand,
        BlockHitResult hitResult,
        BlockPos pos
    ) {
        BlockState targetBlockState = level.getBlockState(pos);
        Property<?> property = AnvilHammerItem.findChangeableProperty(targetBlockState);
        if (!player.isShiftKeyDown()
            && AnvilHammerItem.possibleToUseEnhancedHammerChange(targetBlockState)
            && property != null
        ) {
            if (targetBlockState.getBlock() instanceof IHammerChangeable ihc
                && ihc.checkBlockState(targetBlockState)
                && player.getAbilities().mayBuild
            ) {
                List<BlockState> possibleStates = StateUtil.findPossibleStatesForProperty(targetBlockState, property);
                if (possibleStates.isEmpty()) {
                    new HammerUsePack(pos, hand).send();
                } else {
                    Minecraft.getInstance().setScreen(new AnvilHammerScreen(
                        pos,
                        targetBlockState,
                        property,
                        possibleStates
                    ));
                }
            }
        } else {
            new HammerUsePack(pos, hand).send();
        }
    }
}
