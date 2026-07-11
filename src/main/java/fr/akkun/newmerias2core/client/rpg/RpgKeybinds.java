package fr.akkun.newmerias2core.client.rpg;

import com.mojang.blaze3d.platform.InputConstants;
import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.rpg.network.CastSpellPayload;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = NewmeriaS2Core.MOD_ID, value = Dist.CLIENT)
public class RpgKeybinds {
    public static final KeyMapping OPEN_STATS = new KeyMapping("key.newmerias2core.open_stats",
            GLFW.GLFW_KEY_K, KeyMapping.Category.MISC);
    // M alone casts the selected spell; Ctrl+M opens the spell selection menu instead (checked on press).
    public static final KeyMapping CAST_SPELL = new KeyMapping("key.newmerias2core.cast_spell",
            GLFW.GLFW_KEY_M, KeyMapping.Category.MISC);

    @SubscribeEvent
    static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_STATS);
        event.register(CAST_SPELL);
    }

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_STATS.consumeClick()) {
            if (Minecraft.getInstance().gui.screen() == null) {
                Minecraft.getInstance().gui.setScreen(new StatScreen());
            }
        }
        while (CAST_SPELL.consumeClick()) {
            if (Minecraft.getInstance().gui.screen() != null) {
                continue;
            }
            if (isControlDown()) {
                Minecraft.getInstance().gui.setScreen(new MagicScreen());
            } else {
                ClientPacketDistributor.sendToServer(new CastSpellPayload());
            }
        }
    }

    private static boolean isControlDown() {
        var window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }
}
