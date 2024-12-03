package dev.dubhe.anvilcraft.api.hammer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 铁砧锤选择效果
 */
public interface IHasHammerEffect {
    boolean shouldRender();

    BlockPos renderingBlockPos();

    BlockState renderingBlockState();

    RenderType renderType();

}
