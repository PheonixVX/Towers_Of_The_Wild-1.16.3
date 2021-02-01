package io.github.ender.towersofthewild.mixins;

import com.google.common.collect.ImmutableMap;
import io.github.ender.towersofthewild.register.TowerStructuresRegistry;
import io.github.ender.towersofthewild.setup.WorldSetup;
import io.github.ender.towersofthewild.structures.AbstractTowerStructure;
import io.github.ender.towersofthewild.structures.pieces.TowerPools;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
	@Inject(method = "addPlainsFeatures(Lnet/minecraft/world/biome/GenerationSettings$Builder;)V", at = @At("TAIL"))
	private static void addPlainsFeatures(GenerationSettings.Builder builder, CallbackInfo ci) {
		ConfiguredStructureFeature<?, ?> CONFIGURED_STRUCTURE = null;
		for (StructureFeature<?> ro : TowerStructuresRegistry.STRUCTURE_FEATURES) {
			AbstractTowerStructure structure = (AbstractTowerStructure) ro;
			/*StructuresConfig.DEFAULT_STRUCTURES = // Default structures
				ImmutableMap.<StructureFeature<?>, StructureConfig>builder()
					.putAll(StructuresConfig.DEFAULT_STRUCTURES)
					.put(structure, new StructureConfig(structure.getDistance(), structure.getSeparation(), structure.getSeedModifier()))
					.build();
*/
			/*ChunkGeneratorSettings.INSTANCE.getStructuresConfig().structures.put(structure, new StructureConfig(structure.getDistance(), structure.getSeparation(), structure.getSeedModifier()));
			FabricStructureBuilder.create(new Identifier(structure.getName()), structure)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES)
				.defaultConfig( new StructureConfig(structure.getDistance(), structure.getSeparation(), structure.getSeedModifier()))
				.adjustsSurface();*/
			builder.structureFeature(CONFIGURED_STRUCTURE);
		}
	}
}