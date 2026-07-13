package fr.akkun.newmerias2core;

import com.mojang.logging.LogUtils;
import fr.akkun.newmerias2core.block.ModBlocks;
import fr.akkun.newmerias2core.creativemodetab.ModCreativeModeTabs;
import fr.akkun.newmerias2core.data.ModDataComponents;
import fr.akkun.newmerias2core.effect.ModEffects;
import fr.akkun.newmerias2core.entity.ModEntityTypes;
import fr.akkun.newmerias2core.item.ModItems;
import fr.akkun.newmerias2core.potion.ModPotions;
import fr.akkun.newmerias2core.rpg.RpgAttachments;
import fr.akkun.newmerias2core.rpg.network.RpgNetworking;
import fr.akkun.newmerias2core.sound.ModSounds;
import fr.akkun.newmerias2core.stat.ModStats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NewmeriaS2Core.MOD_ID)
public class NewmeriaS2Core {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "newmerias2core";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NewmeriaS2Core(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDataComponents.register(modEventBus);
        ModStats.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEffects.register(modEventBus);

        ModPotions.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        RpgAttachments.register(modEventBus);
        RpgNetworking.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
