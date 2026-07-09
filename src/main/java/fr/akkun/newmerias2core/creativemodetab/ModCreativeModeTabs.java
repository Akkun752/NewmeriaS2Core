package fr.akkun.newmerias2core.creativemodetab;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NewmeriaS2Core.MOD_ID);

    public static final Supplier<CreativeModeTab> NEWMERIA_TAB = CREATIVE_MODE_TABS.register("newmeria_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PEER.get()))
                    .title(Component.translatable("creativetab.newmerias2core.newmeria"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PEER);
                        output.accept(ModItems.RICE_SHOOT);
                        output.accept(ModItems.RICE);
                    }).build());

    public static final Supplier<CreativeModeTab> NEWMERIA_SPECIAL_TAB = CREATIVE_MODE_TABS.register("newmeria_special_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AKKUN_S1_TOTEM.get()))
                    .title(Component.translatable("creativetab.newmerias2core.newmeria_special"))
                    .displayItems((itemDisplayParameters, output) -> {
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
