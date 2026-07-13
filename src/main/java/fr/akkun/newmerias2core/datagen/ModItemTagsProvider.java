package fr.akkun.newmerias2core.datagen;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, NewmeriaS2Core.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ItemTags.STONE_TOOL_MATERIALS).add(ModItems.getRK(ModItems.COBBLED_MARBLE.get()));
        tag(ItemTags.SMELTS_TO_GLASS).add(ModItems.getRK(ModItems.BLACK_SAND.get()));
    }
}
