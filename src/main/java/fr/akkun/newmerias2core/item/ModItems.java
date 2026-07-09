package fr.akkun.newmerias2core.item;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.block.ModBlocks;
import fr.akkun.newmerias2core.food.ModFoods;
import fr.akkun.newmerias2core.item.custom.TotemItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NewmeriaS2Core.MOD_ID);

    public static final DeferredItem<Item> PEER = ITEMS.registerItem("peer",
            properties -> new Item(properties.food(ModFoods.PEER)));

    public static final DeferredItem<Item> RICE_SHOOT = ITEMS.registerItem("rice_shoot",
            properties -> new PlaceOnWaterBlockItem(ModBlocks.RICE_CROP.get(), properties));

    public static final DeferredItem<Item> RICE = ITEMS.registerItem("rice",
            properties -> new Item(properties.food(ModFoods.RICE)));

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
