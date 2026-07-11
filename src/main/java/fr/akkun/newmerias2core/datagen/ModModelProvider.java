package fr.akkun.newmerias2core.datagen;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.block.ModBlocks;
import fr.akkun.newmerias2core.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.CropBlock;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, NewmeriaS2Core.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.PEER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RICE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI_PEPPER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI_RICE.get(), ModelTemplates.FLAT_ITEM);
        blockModels.createCropBlock(ModBlocks.RICE_CROP.get(), CropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7);
        blockModels.createCropBlock(ModBlocks.CHILI_CROP.get(), BeetrootBlock.AGE, 0, 1, 2, 3);

        itemModels.generateFlatItem(ModItems.AKKUN_S1_TOTEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FALNIX_S1_TOTEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAPHAAILE_S1_TOTEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WOOHTYTI_S1_TOTEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BATS_S1_TOTEM.get(), ModelTemplates.FLAT_ITEM);
    }
}
