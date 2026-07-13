package fr.akkun.newmerias2core.client.rpg;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import fr.akkun.newmerias2core.rpg.RpgData;
import fr.akkun.newmerias2core.rpg.RpgSpell;
import fr.akkun.newmerias2core.rpg.network.SelectSpellPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.EnumMap;
import java.util.Map;

/** Lists spells in unlock order; only one can be selected as the active spell (cast with the M key). */
public class MagicScreen extends Screen {
    private static final int ROW_HEIGHT = 24;
    private static final int BUTTON_WIDTH = 100;
    private static final RpgSpell[] SPELLS = RpgSpell.values();

    private final Map<RpgSpell, Button> buttons = new EnumMap<>(RpgSpell.class);

    public MagicScreen() {
        super(Component.translatable("gui.newmerias2core.magic.title"));
    }

    @Override
    protected void init() {
        super.init();
        int top = topOfRows();
        int centerX = this.width / 2;
        for (int i = 0; i < SPELLS.length; i++) {
            RpgSpell spell = SPELLS[i];
            int y = top + i * ROW_HEIGHT;
            Button button = Button.builder(Component.empty(), b -> onSelect(spell))
                    .bounds(centerX + 10, y, BUTTON_WIDTH, 20)
                    .build();
            this.buttons.put(spell, button);
            this.addRenderableWidget(button);
        }
        refreshButtons();
    }

    @Override
    public void tick() {
        super.tick();
        refreshButtons();
    }

    private int topOfRows() {
        return this.height / 2 - (SPELLS.length * ROW_HEIGHT) / 2;
    }

    private RpgData currentData() {
        var player = Minecraft.getInstance().player;
        return player == null ? RpgData.DEFAULT : player.getData(RpgAttachments.RPG_DATA);
    }

    private void refreshButtons() {
        RpgData data = currentData();
        for (Map.Entry<RpgSpell, Button> entry : buttons.entrySet()) {
            RpgSpell spell = entry.getKey();
            Button button = entry.getValue();
            boolean unlocked = spell.isUnlocked(data.magicLevel());
            boolean selected = data.selectedSpell() == spell.ordinal();
            button.active = unlocked && !selected;
            button.setMessage(Component.translatable(selected ? "gui.newmerias2core.magic.selected"
                    : unlocked ? "gui.newmerias2core.magic.select" : "gui.newmerias2core.magic.locked"));
        }
    }

    private void onSelect(RpgSpell spell) {
        ClientPacketDistributor.sendToServer(new SelectSpellPayload(spell.ordinal()));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int top = topOfRows();
        int centerX = this.width / 2;

        graphics.centeredText(this.font, this.title, centerX, top - 24, -1);

        for (int i = 0; i < SPELLS.length; i++) {
            RpgSpell spell = SPELLS[i];
            int y = top + i * ROW_HEIGHT + 6;
            graphics.text(this.font, spell.displayName(), centerX - 100, y, -1);
        }
    }
}
