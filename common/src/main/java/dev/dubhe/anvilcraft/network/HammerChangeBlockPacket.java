package dev.dubhe.anvilcraft.network;

import dev.anvilcraft.lib.network.Packet;
import dev.dubhe.anvilcraft.init.ModNetworks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record HammerChangeBlockPacket(
    BlockPos pos,
    BlockState blockState
) implements Packet {
    @Override
    public ResourceLocation getType() {
        return ModNetworks.CLIENT_HAMMER_CHANGE_BLOCK;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(Block.getId(this.blockState));
    }

    /**
     *
     */
    public static HammerChangeBlockPacket decode(FriendlyByteBuf buf) {
        return new HammerChangeBlockPacket(
            buf.readBlockPos(),
            Block.stateById(buf.readInt())
        );
    }

    @Override
    public void handler(@NotNull MinecraftServer server, ServerPlayer player) {
        Level level = player.level();
        if (level.isLoaded(pos)) {
            level.setBlock(pos, blockState, Block.UPDATE_ALL_IMMEDIATE);
        }
    }
}
