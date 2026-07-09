package fr.akkun.newmerias2core.client.rpg;

import com.mojang.blaze3d.platform.Window;
import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.rpg.RpgAttachments;
import fr.akkun.newmerias2core.rpg.RpgData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/**
 * Renders a bar directly above the vanilla XP bar (same system: background/progress sprites
 * plus a centered level number), showing progress toward the next RPG level. Also nudges the
 * vanilla health/armor/food/air rows up so they don't render behind this extra bar.
 */
@EventBusSubscriber(modid = NewmeriaS2Core.MOD_ID, value = Dist.CLIENT)
public class RpgHudLayer {
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "rpg_level_bar");
    private static final Identifier BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "hud/rpg_bar_background");
    private static final Identifier PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "hud/rpg_bar_progress");
    // Must match the actual pixel size of hud/rpg_bar_background.png and hud/rpg_bar_progress.png
    // (currently 182x5, same as vanilla's XP bar) - a mismatch here stretches the sprite and blurs it.
    private static final int WIDTH = 182;
    private static final int HEIGHT = 5;
    private static final int GAP = 1;
    private static final int SHIFT_UP = HEIGHT + GAP;

    @SubscribeEvent
    static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        // Registered below the whole XP bar stack (background, level number, bar) so it renders
        // behind them instead of covering the vanilla XP level number.
        event.registerBelow(VanillaGuiLayers.CONTEXTUAL_INFO_BAR_BACKGROUND, LAYER_ID, RpgHudLayer::render);
        event.wrapLayer(VanillaGuiLayers.PLAYER_HEALTH, layer -> shiftUp(layer, SHIFT_UP));
        event.wrapLayer(VanillaGuiLayers.ARMOR_LEVEL, layer -> shiftUp(layer, SHIFT_UP));
        event.wrapLayer(VanillaGuiLayers.FOOD_LEVEL, layer -> shiftUp(layer, SHIFT_UP));
        event.wrapLayer(VanillaGuiLayers.AIR_LEVEL, layer -> shiftUp(layer, SHIFT_UP));
    }

    private static GuiLayer shiftUp(GuiLayer original, int offset) {
        return (graphics, deltaTracker) -> {
            graphics.pose().pushMatrix();
            graphics.pose().translate(0, -offset);
            original.render(graphics, deltaTracker);
            graphics.pose().popMatrix();
        };
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || !minecraft.gameMode.canHurtPlayer()) {
            // canHurtPlayer() is false in creative/spectator - same check vanilla uses to hide health/food/armor/air.
            return;
        }
        RpgData data = player.getData(RpgAttachments.RPG_DATA);

        Window window = minecraft.getWindow();
        int left = (window.getGuiScaledWidth() - WIDTH) / 2;
        int xpBarTop = window.getGuiScaledHeight() - 24 - 5;
        int top = xpBarTop - GAP - HEIGHT;

        int progress;
        if (data.level() >= RpgData.MAX_RPG_LEVEL) {
            progress = WIDTH;
        } else {
            int threshold = RpgData.pointsToReachNextLevel(data.level());
            progress = threshold > 0 ? (int) (Math.min(1f, data.pointsIntoLevel() / (float) threshold) * (WIDTH + 1)) : 0;
        }

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, left, top, WIDTH, HEIGHT);
        if (progress > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_SPRITE, WIDTH, HEIGHT, 0, 0, left, top, progress, HEIGHT);
        }

        drawLevelNumber(graphics, minecraft, data.level(), top);
    }

    private static void drawLevelNumber(GuiGraphicsExtractor graphics, Minecraft minecraft, int level, int barTop) {
        Component str = Component.translatable("gui.newmerias2core.rpg_level", level);
        int x = (graphics.guiWidth() - minecraft.font.width(str)) / 2;
        int y = barTop - 6;
        graphics.text(minecraft.font, str, x + 1, y, -16777216, false);
        graphics.text(minecraft.font, str, x - 1, y, -16777216, false);
        graphics.text(minecraft.font, str, x, y + 1, -16777216, false);
        graphics.text(minecraft.font, str, x, y - 1, -16777216, false);
        graphics.text(minecraft.font, str, x, y, 0xFFFF5555, false);
    }
}
