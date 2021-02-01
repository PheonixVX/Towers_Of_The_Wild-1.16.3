package io.github.ender.towersofthewild.register;

import io.github.ender.towersofthewild.TowersOfTheWild;
import io.github.ender.towersofthewild.structures.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.ArrayList;
import java.util.List;

public class TowerStructuresRegistry {

	public static final List<StructureFeature<StructurePoolFeatureConfig>> STRUCTURE_FEATURES = new ArrayList<>();

	public static final StructureFeature<StructurePoolFeatureConfig> TOWER = register("tower", new TowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> ICE_TOWER = register("ice_tower", new IceTowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> JUNGLE_TOWER = register("jungle_tower", new JungleTowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> DERELICT_TOWER = register("derelict_tower", new DerelictTowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> DERELICT_GRASS_TOWER = register("derelict_grass_tower", new DerelictGrassTowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> OCEAN_TOWER = register("ocean_tower", new OceanTowerStructure(StructurePoolFeatureConfig.CODEC));
	public static final StructureFeature<StructurePoolFeatureConfig> OCEAN_WARM_TOWER = register("ocean_warm_tower", new OceanWarmTowerStructure(StructurePoolFeatureConfig.CODEC));

	private static <T extends StructureFeature<?>> StructureFeature register(String name, T structure) {
		TowersOfTheWild.LOGGER.info(name + " structure registered");
		StructureFeature.STRUCTURES.put(TowersOfTheWild.MOD_ID + ":" + name, structure);
		StructureFeature.STRUCTURE_TO_GENERATION_STEP.put(structure, GenerationStep.Feature.SURFACE_STRUCTURES);

		return Registry.register(
			Registry.STRUCTURE_FEATURE,
			new Identifier(TowersOfTheWild.MOD_ID, name),
			structure
		);
	}

	static {
		STRUCTURE_FEATURES.add(TOWER);
		STRUCTURE_FEATURES.add(ICE_TOWER);
		STRUCTURE_FEATURES.add(JUNGLE_TOWER);
		STRUCTURE_FEATURES.add(DERELICT_TOWER);
		STRUCTURE_FEATURES.add(DERELICT_GRASS_TOWER);
		STRUCTURE_FEATURES.add(OCEAN_TOWER);
		STRUCTURE_FEATURES.add(OCEAN_WARM_TOWER);
	}
}
