package bl4ckscor3.mod.chanceglobe;

import java.util.Optional;
import java.util.function.Supplier;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.config.ModConfig;

public class FabricEntrypoint implements ModInitializer, Platform {
	@Override
	public void onInitialize() {
		ChanceGlobe.initialize(this);
		ConfigRegistry.INSTANCE.register(ChanceGlobe.MODID, ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
		ModConfigEvents.reloading(ChanceGlobe.MODID).register(_ -> ChanceGlobe.generateItemStacks());
		ServerLifecycleEvents.SERVER_STARTED.register(_ -> ChanceGlobe.generateItemStacks());
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(ChanceGlobe.CHANCE_GLOBE_ITEM.get()));
	}

	@Override
	public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityFactory<T> factory, Block validBlock) {
		return FabricBlockEntityTypeBuilder.create(factory::create, validBlock).build();
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public <R, T extends R> void register(ResourceKey<? extends Registry<R>> registryKey, Supplier<T> entry, String path) {
		Optional<Holder.Reference<R>> registry = BuiltInRegistries.REGISTRY.get((ResourceKey) registryKey);

		if (registry.isEmpty()) {
			throw new IllegalArgumentException("Couldn't find registry " + registryKey);
		}

		Registry.register((Registry<R>) registry.get().value(), ChanceGlobe.id(path), entry.get());
	}
}
