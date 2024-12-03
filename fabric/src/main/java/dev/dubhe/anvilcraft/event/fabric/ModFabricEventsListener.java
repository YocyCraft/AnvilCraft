package dev.dubhe.anvilcraft.event.fabric;

import net.minecraft.client.Minecraft;

public class ModFabricEventsListener {
    /**
     * 初始化
     */
    public static void init() {
        ServerLifecycleEventListener.init();
        BlockEventListener.init();
        LootTableEventListener.init();
        LightningEventListener.init();
        AnvilEntityEventListener.init();
        PlayerEventListener.init();
        CommandEventListener.init();
        ServerWorldEventListener.init();
        CommonEventHandlerListener.serverInit();
        try {
            Minecraft.getInstance();
            RenderEventListener.init();
        } catch (Throwable e) {
            //intentially empty
        }

    }
}
