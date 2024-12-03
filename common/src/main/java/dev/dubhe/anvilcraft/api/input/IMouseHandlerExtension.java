package dev.dubhe.anvilcraft.api.input;

import net.minecraft.client.MouseHandler;

/**
 * {@link MouseHandler} 扩展
 */
public interface IMouseHandlerExtension {
    void anvilCraft$grabMouseWithScreen();

    static IMouseHandlerExtension of(MouseHandler mouseHandler) {
        return (IMouseHandlerExtension) mouseHandler;
    }
}
