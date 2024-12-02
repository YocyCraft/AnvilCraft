package dev.dubhe.anvilcraft.client.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.dubhe.anvilcraft.AnvilCraft;
import lombok.Getter;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModShaders {
    @Getter
    private static final List<ShaderMetadata> shaders = new ArrayList<>();

    static {
        shaders.add(
            new ShaderMetadata(
                AnvilCraft.of("ring"),
                DefaultVertexFormat.POSITION_COLOR,
                it -> ringShader = it
            )
        );
        shaders.add(
            new ShaderMetadata(
                AnvilCraft.of("selection"),
                DefaultVertexFormat.POSITION_COLOR,
                it -> selectionShader = it
            )
        );
        shaders.add(
            new ShaderMetadata(
                AnvilCraft.of("rendertype_translucent_colored_overlay"),
                DefaultVertexFormat.BLOCK,
                it -> renderTypeColoredOverlayShader = it
            )
        );
    }

    @Getter
    static ShaderInstance renderTypeColoredOverlayShader;
    @Getter
    private static ShaderInstance ringShader;
    @Getter
    private static ShaderInstance selectionShader;

    public record ShaderMetadata(
        ResourceLocation location,
        VertexFormat vertexFormat,
        Consumer<ShaderInstance> callback
    ) {
    }
}
