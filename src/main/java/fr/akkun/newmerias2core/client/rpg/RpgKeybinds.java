package fr.akkun.newmerias2core.client.rpg;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = NewmeriaS2Core.MOD_ID, value = Dist.CLIENT)
public class RpgKeybinds {
    public static final KeyMapping OPEN_STATS = new KeyMapping("key.newmerias2core.open_stats",
            GLFW.GLFW_KEY_K, KeyMapping.Category.MISC);

    @SubscribeEvent
    static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_STATS);
    }

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_STATS.consumeClick()) {
            if (Minecraft.getInstance().gui.screen() == null) {
                Minecraft.getInstance().gui.setScreen(new StatScreen());
            }
        }
    }
}
