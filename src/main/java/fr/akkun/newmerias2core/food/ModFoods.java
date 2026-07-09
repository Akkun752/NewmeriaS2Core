package fr.akkun.newmerias2core.food;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    // Apple: nutrition 4, saturationModifier 0.3 (2.4 saturation restored)
    public static final FoodProperties PEER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.34f).build();

    // Light snack, like a small bag of rice
    public static final FoodProperties RICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
}
