package fr.akkun.newmerias2core.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class ModFoods {
    // Apple: nutrition 4, saturationModifier 0.3 (2.4 saturation restored)
    public static final FoodProperties PEER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.34f).build();

    // Light snack, like a small bag of rice
    public static final FoodProperties RICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();

    public static final FoodProperties CHILI_PEPPER = new FoodProperties.Builder().nutrition(4).saturationModifier(0.32f).build();

    // Same as Golden Carrot, always edible regardless of hunger
    public static final FoodProperties CHILI_RICE = new FoodProperties.Builder().nutrition(6).saturationModifier(1.2f).alwaysEdible().build();

    public static final Consumable CHILI_RICE_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200),
                    new MobEffectInstance(MobEffects.REGENERATION, 100)
            ))).build();
}
