package fr.akkun.newmerias2core.creativemodetab;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.item.ModItems;
import fr.akkun.newmerias2core.potion.ModPotions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NewmeriaS2Core.MOD_ID);

    public static final Supplier<CreativeModeTab> NEWMERIA_TAB = CREATIVE_MODE_TABS.register("newmeria_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.newmerias2core.newmeria"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PEER);
                        output.accept(ModItems.RICE_SHOOT);
                        output.accept(ModItems.RICE);
                        output.accept(ModItems.CHILI_PEPPER);
                        output.accept(ModItems.CHILI_RICE);
                        output.accept(ModItems.CHILI_SEEDS);
                        output.accept(ModItems.SANDWICH);

                        output.accept(ModPotions.createStack(Items.POTION, ModPotions.LIMONADE, ModPotions.LIMONADE_COLOR));
                        output.accept(ModPotions.createStack(Items.POTION, ModPotions.DIABOLO_SWEET_BERRIES, ModPotions.DIABOLO_SWEET_BERRIES_COLOR));
                        output.accept(ModPotions.createStack(Items.POTION, ModPotions.DIABOLO_GLOW_BERRIES, ModPotions.DIABOLO_GLOW_BERRIES_COLOR));

                        output.accept(ModItems.SAPPHIRE);
                        output.accept(ModItems.SAPPHIRE_ORE);
                        output.accept(ModItems.DEEPSLATE_SAPPHIRE_ORE);
                        output.accept(ModItems.SAPPHIRE_BLOCK);
                        output.accept(ModItems.OBSIDIAN_STICK);

                        output.accept(ModItems.BLACK_SAND);
                        output.accept(ModItems.BLACK_SANDSTONE);
                        output.accept(ModItems.CHISELED_BLACK_SANDSTONE);
                        output.accept(ModItems.CUT_BLACK_SANDSTONE);
                        output.accept(ModItems.SMOOTH_BLACK_SANDSTONE);
                        output.accept(ModItems.BLACK_SANDSTONE_SLAB);
                        output.accept(ModItems.CUT_BLACK_SANDSTONE_SLAB);
                        output.accept(ModItems.SMOOTH_BLACK_SANDSTONE_SLAB);
                        output.accept(ModItems.BLACK_SANDSTONE_STAIRS);
                        output.accept(ModItems.SMOOTH_BLACK_SANDSTONE_STAIRS);
                        output.accept(ModItems.BLACK_SANDSTONE_WALL);

                        output.accept(ModItems.MARBLE);
                        output.accept(ModItems.COBBLED_MARBLE);
                        output.accept(ModItems.COBBLED_MARBLE_STAIRS);
                        output.accept(ModItems.COBBLED_MARBLE_SLAB);
                        output.accept(ModItems.COBBLED_MARBLE_WALL);
                        output.accept(ModItems.MARBLE_PRESSURE_PLATE);
                        output.accept(ModItems.MARBLE_BUTTON);
                        output.accept(ModItems.MARBLE_SLAB);
                        output.accept(ModItems.MARBLE_STAIRS);
                        output.accept(ModItems.MARBLE_BRICKS);
                        output.accept(ModItems.CHISELED_MARBLE_BRICKS);
                        output.accept(ModItems.CRACKED_MARBLE_BRICKS);
                        output.accept(ModItems.MARBLE_BRICK_SLAB);
                        output.accept(ModItems.MARBLE_BRICK_STAIRS);
                        output.accept(ModItems.MARBLE_BRICK_WALL);
                        output.accept(ModItems.MOSSY_MARBLE_BRICKS);
                        output.accept(ModItems.MOSSY_MARBLE_BRICK_SLAB);
                        output.accept(ModItems.MOSSY_MARBLE_BRICK_STAIRS);
                        output.accept(ModItems.MOSSY_MARBLE_BRICK_WALL);

                        output.accept(ModItems.AKKUN_S1_TOTEM);
                        output.accept(ModItems.BATS_S1_TOTEM);
                        output.accept(ModItems.FALNIX_S1_TOTEM);
                        output.accept(ModItems.RAPHAAILE_S1_TOTEM);
                        output.accept(ModItems.WOOHTYTI_S1_TOTEM);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
