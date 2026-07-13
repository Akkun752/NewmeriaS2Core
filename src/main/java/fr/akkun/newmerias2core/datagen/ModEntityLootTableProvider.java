package fr.akkun.newmerias2core.datagen;

import fr.akkun.newmerias2core.entity.ModEntityTypes;
import fr.akkun.newmerias2core.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

/**
 * Implements the plain {@link LootTableSubProvider} interface directly instead of extending vanilla's
 * {@link net.minecraft.data.loot.EntityLootSubProvider}: that base class validates that every entity
 * type known to the game (including all vanilla mobs) has a registered loot table, which only makes
 * sense for the single provider meant to cover the whole game - not a mod-scoped provider like this one.
 */
public class ModEntityLootTableProvider implements LootTableSubProvider {
    public ModEntityLootTableProvider(HolderLookup.Provider registries) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        ResourceKey<LootTable> snowWalkerTable = ModEntityTypes.SNOW_WALKER.get().getDefaultLootTable().orElseThrow();
        // Two independent rolls: 4% chance for 1 sapphire, and separately 1% chance for 2 more.
        output.accept(snowWalkerTable, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.SAPPHIRE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .when(LootItemRandomChanceCondition.randomChance(0.04F))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.SAPPHIRE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                                .when(LootItemRandomChanceCondition.randomChance(0.01F)))));
    }
}
