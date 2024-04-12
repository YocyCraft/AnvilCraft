package dev.dubhe.anvilcraft.listener;

import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = AnvilCraft.MOD_ID)
public class LootTableListener {
    @SubscribeEvent
    public static void lootTable(@NotNull LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        LootTable table = event.getTable();
        if (Blocks.BUDDING_AMETHYST.getLootTable().equals(id)) {
            table.addPool(new LootPool.Builder()
                    .add(LootItem.lootTableItem(ModItems.GEODE))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                    .build()
            );
        }
        if (BuiltInLootTables.SPAWN_BONUS_CHEST.equals(id)) {
            table.addPool(new LootPool.Builder()
                    .add(LootItem.lootTableItem(ModItems.GEODE))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))
                    .build()
            );
        }
        event.setTable(table);
    }
}
