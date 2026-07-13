package fr.akkun.newmerias2core.item;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.block.ModBlocks;
import fr.akkun.newmerias2core.entity.ModEntityTypes;
import fr.akkun.newmerias2core.food.ModFoods;
import fr.akkun.newmerias2core.item.custom.TotemItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NewmeriaS2Core.MOD_ID);

    public static final DeferredItem<Item> PEER = ITEMS.registerItem("peer",
            properties -> new Item(properties.food(ModFoods.PEER)));

    public static final DeferredItem<Item> RICE_SHOOT = ITEMS.registerItem("rice_shoot",
            properties -> new BlockItem(ModBlocks.RICE_CROP.get(), properties));

    public static final DeferredItem<Item> RICE = ITEMS.registerItem("rice",
            properties -> new Item(properties.food(ModFoods.RICE)));

    public static final DeferredItem<Item> CHILI_PEPPER = ITEMS.registerItem("chili_pepper",
            properties -> new Item(properties.food(ModFoods.CHILI_PEPPER)));

    public static final DeferredItem<Item> CHILI_SEEDS = ITEMS.registerItem("chili_seeds",
            properties -> new BlockItem(ModBlocks.CHILI_CROP.get(), properties));

    public static final DeferredItem<Item> CHILI_RICE = ITEMS.registerItem("chili_rice",
            properties -> new Item(properties.stacksTo(1).food(ModFoods.CHILI_RICE, ModFoods.CHILI_RICE_CONSUMABLE).usingConvertsTo(Items.BOWL)));

    public static final DeferredItem<Item> SANDWICH = ITEMS.registerItem("sandwich",
            properties -> new Item(properties.food(ModFoods.SANDWICH)));

    public static final DeferredItem<Item> SAPPHIRE = ITEMS.registerItem("sapphire",
            properties -> new Item(properties));

    public static final DeferredItem<Item> SAPPHIRE_SWORD = ITEMS.registerItem("sapphire_sword",
            properties -> new Item(properties.sword(ModToolTiers.SAPPHIRE, 3.0F, -2.4F)));

    public static final DeferredItem<Item> SNOW_WALKER_SPAWN_EGG = ITEMS.registerItem("snow_walker_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntityTypes.SNOW_WALKER.get())));

    public static final DeferredItem<Item> SAPPHIRE_ORE = ITEMS.registerItem("sapphire_ore",
            properties -> new BlockItem(ModBlocks.SAPPHIRE_ORE.get(), properties.useBlockDescriptionPrefix()));

    public static final DeferredItem<Item> DEEPSLATE_SAPPHIRE_ORE = ITEMS.registerItem("deepslate_sapphire_ore",
            properties -> new BlockItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), properties.useBlockDescriptionPrefix()));

    public static final DeferredItem<Item> SAPPHIRE_BLOCK = ITEMS.registerItem("sapphire_block",
            properties -> new BlockItem(ModBlocks.SAPPHIRE_BLOCK.get(), properties.useBlockDescriptionPrefix()));

    public static final DeferredItem<Item> OBSIDIAN_STICK = ITEMS.registerItem("obsidian_stick",
            properties -> new Item(properties));

    public static final DeferredItem<Item> BLACK_SAND = ITEMS.registerItem("black_sand",
            properties -> new BlockItem(ModBlocks.BLACK_SAND.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_SANDSTONE = ITEMS.registerItem("black_sandstone",
            properties -> new BlockItem(ModBlocks.BLACK_SANDSTONE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CHISELED_BLACK_SANDSTONE = ITEMS.registerItem("chiseled_black_sandstone",
            properties -> new BlockItem(ModBlocks.CHISELED_BLACK_SANDSTONE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CUT_BLACK_SANDSTONE = ITEMS.registerItem("cut_black_sandstone",
            properties -> new BlockItem(ModBlocks.CUT_BLACK_SANDSTONE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> SMOOTH_BLACK_SANDSTONE = ITEMS.registerItem("smooth_black_sandstone",
            properties -> new BlockItem(ModBlocks.SMOOTH_BLACK_SANDSTONE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_SANDSTONE_SLAB = ITEMS.registerItem("black_sandstone_slab",
            properties -> new BlockItem(ModBlocks.BLACK_SANDSTONE_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CUT_BLACK_SANDSTONE_SLAB = ITEMS.registerItem("cut_black_sandstone_slab",
            properties -> new BlockItem(ModBlocks.CUT_BLACK_SANDSTONE_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> SMOOTH_BLACK_SANDSTONE_SLAB = ITEMS.registerItem("smooth_black_sandstone_slab",
            properties -> new BlockItem(ModBlocks.SMOOTH_BLACK_SANDSTONE_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_SANDSTONE_STAIRS = ITEMS.registerItem("black_sandstone_stairs",
            properties -> new BlockItem(ModBlocks.BLACK_SANDSTONE_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> SMOOTH_BLACK_SANDSTONE_STAIRS = ITEMS.registerItem("smooth_black_sandstone_stairs",
            properties -> new BlockItem(ModBlocks.SMOOTH_BLACK_SANDSTONE_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_SANDSTONE_WALL = ITEMS.registerItem("black_sandstone_wall",
            properties -> new BlockItem(ModBlocks.BLACK_SANDSTONE_WALL.get(), properties.useBlockDescriptionPrefix()));

    public static final DeferredItem<Item> MARBLE = ITEMS.registerItem("marble",
            properties -> new BlockItem(ModBlocks.MARBLE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> COBBLED_MARBLE = ITEMS.registerItem("cobbled_marble",
            properties -> new BlockItem(ModBlocks.COBBLED_MARBLE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> COBBLED_MARBLE_STAIRS = ITEMS.registerItem("cobbled_marble_stairs",
            properties -> new BlockItem(ModBlocks.COBBLED_MARBLE_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> COBBLED_MARBLE_SLAB = ITEMS.registerItem("cobbled_marble_slab",
            properties -> new BlockItem(ModBlocks.COBBLED_MARBLE_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> COBBLED_MARBLE_WALL = ITEMS.registerItem("cobbled_marble_wall",
            properties -> new BlockItem(ModBlocks.COBBLED_MARBLE_WALL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_PRESSURE_PLATE = ITEMS.registerItem("marble_pressure_plate",
            properties -> new BlockItem(ModBlocks.MARBLE_PRESSURE_PLATE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_BUTTON = ITEMS.registerItem("marble_button",
            properties -> new BlockItem(ModBlocks.MARBLE_BUTTON.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_SLAB = ITEMS.registerItem("marble_slab",
            properties -> new BlockItem(ModBlocks.MARBLE_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_STAIRS = ITEMS.registerItem("marble_stairs",
            properties -> new BlockItem(ModBlocks.MARBLE_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_BRICKS = ITEMS.registerItem("marble_bricks",
            properties -> new BlockItem(ModBlocks.MARBLE_BRICKS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CHISELED_MARBLE_BRICKS = ITEMS.registerItem("chiseled_marble_bricks",
            properties -> new BlockItem(ModBlocks.CHISELED_MARBLE_BRICKS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CRACKED_MARBLE_BRICKS = ITEMS.registerItem("cracked_marble_bricks",
            properties -> new BlockItem(ModBlocks.CRACKED_MARBLE_BRICKS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_BRICK_SLAB = ITEMS.registerItem("marble_brick_slab",
            properties -> new BlockItem(ModBlocks.MARBLE_BRICK_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_BRICK_STAIRS = ITEMS.registerItem("marble_brick_stairs",
            properties -> new BlockItem(ModBlocks.MARBLE_BRICK_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MARBLE_BRICK_WALL = ITEMS.registerItem("marble_brick_wall",
            properties -> new BlockItem(ModBlocks.MARBLE_BRICK_WALL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICKS = ITEMS.registerItem("mossy_marble_bricks",
            properties -> new BlockItem(ModBlocks.MOSSY_MARBLE_BRICKS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_SLAB = ITEMS.registerItem("mossy_marble_brick_slab",
            properties -> new BlockItem(ModBlocks.MOSSY_MARBLE_BRICK_SLAB.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_STAIRS = ITEMS.registerItem("mossy_marble_brick_stairs",
            properties -> new BlockItem(ModBlocks.MOSSY_MARBLE_BRICK_STAIRS.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MOSSY_MARBLE_BRICK_WALL = ITEMS.registerItem("mossy_marble_brick_wall",
            properties -> new BlockItem(ModBlocks.MOSSY_MARBLE_BRICK_WALL.get(), properties.useBlockDescriptionPrefix()));

    public static final DeferredItem<Item> AKKUN_S1_TOTEM = ITEMS.registerItem("akkun_s1_totem",
            properties -> new TotemItem(properties.rarity(Rarity.EPIC).stacksTo(1), "item.newmerias2core.akkun_s1_totem.description"));
    public static final DeferredItem<Item> FALNIX_S1_TOTEM = ITEMS.registerItem("falnix_s1_totem",
            properties -> new TotemItem(properties.rarity(Rarity.EPIC).stacksTo(1), "item.newmerias2core.falnix_s1_totem.description"));
    public static final DeferredItem<Item> RAPHAAILE_S1_TOTEM = ITEMS.registerItem("raphaaile_s1_totem",
            properties -> new TotemItem(properties.rarity(Rarity.EPIC).stacksTo(1), "item.newmerias2core.raphaaile_s1_totem.description"));
    public static final DeferredItem<Item> WOOHTYTI_S1_TOTEM = ITEMS.registerItem("woohtyti_s1_totem",
            properties -> new TotemItem(properties.rarity(Rarity.EPIC).stacksTo(1), "item.newmerias2core.woohtyti_s1_totem.description"));
    public static final DeferredItem<Item> BATS_S1_TOTEM = ITEMS.registerItem("bats_s1_totem",
            properties -> new TotemItem(properties.rarity(Rarity.EPIC).stacksTo(1), "item.newmerias2core.bats_s1_totem.description"));

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
