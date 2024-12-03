package dev.dubhe.anvilcraft.block;

import dev.dubhe.anvilcraft.entity.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class EndDustBlock extends Block {
    public EndDustBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(
        @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
        @NotNull BlockState oldState, boolean movedByPiston
    ) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(
        @NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random
    ) {
        boolean isWater = level.getFluidState(pos.above()).is(FluidTags.WATER);
        if (
                (isWater && FallingBlock.isFree(level.getBlockState(pos.above())))
                || level.getBlockState(pos.above()).getBlock() instanceof FallingBlock
        ) {
            FloatingBlockEntity._float(level, pos, state, isWater);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(
        @NotNull BlockState state,
        @Nonnull Level level,
        @NotNull BlockPos pos,
        @NotNull Block neighborBlock,
        @NotNull BlockPos neighborPos,
        boolean movedByPiston
    ) {
        if (isEligible(level, pos, neighborPos)) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isEligible(Level level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        return level.getFluidState(neighborPos).is(FluidTags.WATER)
                || level.getBlockState(pos.above()).getBlock() instanceof FallingBlock;
    }
}
