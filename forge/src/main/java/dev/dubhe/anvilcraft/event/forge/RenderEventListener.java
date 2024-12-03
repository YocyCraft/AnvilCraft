package dev.dubhe.anvilcraft.event.forge;

import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.client.init.ModShaders;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = AnvilCraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)

public class RenderEventListener {
    /**
     * 注册shader
     */
    @SubscribeEvent
    public static void onRegisterShader(RegisterShadersEvent event) throws IOException {
        for (ModShaders.ShaderMetadata shader : ModShaders.getShaders()) {
            event.registerShader(
                new ShaderInstance(
                    event.getResourceProvider(),
                    shader.location(),
                    shader.vertexFormat()
                ),
                shader.callback()
            );
        }
    }
}
