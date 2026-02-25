package com.example.saddlecow;

import com.example.saddlecow.entity.SaddleCowEntity;
import com.example.saddlecow.entity.client.SaddleCowRenderer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Cow;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(SaddleCowMod.MOD_ID)
public class SaddleCowMod {
    public static final String MOD_ID = "saddlecow";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Регистрируем новую сущность (копию коровы)
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<SaddleCowEntity>> SADDLE_COW =
            ENTITY_TYPES.register("saddle_cow",
                    () -> EntityType.Builder.<SaddleCowEntity>of(SaddleCowEntity::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F) // Размеры обычной коровы
                            .build("saddle_cow"));

    public SaddleCowMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Регистрируем сущности
        ENTITY_TYPES.register(modEventBus);

        // Регистрируем события для атрибутов и клиента
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerAttributes);

        // Регистрируем обычные события Forge (для обработки кликов)
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Мод Saddle Cow загружен!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Регистрируем рендер для нашей коровы (чтобы она не была прозрачной/фиолетовой)
        EntityRenderers.register(SADDLE_COW.get(), SaddleCowRenderer::new);
    }

    @SubscribeEvent
    public void registerAttributes(EntityAttributeCreationEvent event) {
        // Копируем атрибуты (здоровье, скорость) у обычной коровы
        event.put(SADDLE_COW.get(), Cow.createAttributes().build());
    }
}
