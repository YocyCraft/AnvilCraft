package dev.dubhe.anvilcraft.client.init;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.FastColor;

import static net.minecraft.client.renderer.RenderStateShard.BLOCK_SHEET_MIPPED;
import static net.minecraft.client.renderer.RenderStateShard.LIGHTMAP;
import static net.minecraft.client.renderer.RenderStateShard.TRANSLUCENT_TARGET;
import static net.minecraft.client.renderer.RenderStateShard.TRANSLUCENT_TRANSPARENCY;

public class ModRenderTypes {
    private static RenderStateShard.ShaderStateShard createRenderTypeColoredOverlayShader(int color) {
        return new RenderStateShard.ShaderStateShard(
            ModRenderTypes::supplyNothing
        ) {
            @Override
            public void setupRenderState() {
                this.getShader()
                    .safeGetUniform("OverlayColor")
                    .set(
                        FastColor.ARGB32.red(color) / 255f,
                        FastColor.ARGB32.green(color) / 255f,
                        FastColor.ARGB32.blue(color) / 255f,
                        FastColor.ARGB32.alpha(color) / 255f
                    );
                RenderSystem.setShader(this::getShader);
            }

            private ShaderInstance getShader() {
                return ModShaders.renderTypeColoredOverlayShader;
            }

            @Override
            public void clearRenderState() {

            }
        };
    }

    public static final RenderType TRANSLUCENT_COLORED_OVERLAY = RenderType.create(
        "translucent",
        DefaultVertexFormat.BLOCK,
        VertexFormat.Mode.QUADS,
        786432,
        true,
        true,
        RenderType.CompositeState.builder()
            .setLightmapState(LIGHTMAP)
            .setShaderState(createRenderTypeColoredOverlayShader(0x5566CCFF))
            .setTextureState(BLOCK_SHEET_MIPPED)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(TRANSLUCENT_TARGET)
            .createCompositeState(true)
    );

    private static <T> T supplyNothing() {
        return null;
    }
}
