package fr.akkun.newmerias2core.datagen;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundsProvider extends SoundDefinitionsProvider {
    public ModSoundsProvider(PackOutput output) {
        super(output, NewmeriaS2Core.MOD_ID);
    }

    @Override
    public void registerSounds() {
    }
}
