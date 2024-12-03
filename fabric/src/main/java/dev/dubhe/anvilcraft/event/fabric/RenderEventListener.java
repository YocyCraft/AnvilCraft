package dev.dubhe.anvilcraft.event.fabric;

import dev.dubhe.anvilcraft.client.init.ModShaders;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;

public class RenderEventListener {
    /**
     * 注册shader
     */
    public static void init() {
        for (ModShaders.ShaderMetadata shader : ModShaders.getShaders()) {
            CoreShaderRegistrationCallback.EVENT.register(ctx -> {
                ctx.register(
                    shader.location(),
                    shader.vertexFormat(),
                    shader.callback()
                );
            });
        }

    }
}
