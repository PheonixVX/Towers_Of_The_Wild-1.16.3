package io.github.ender.towersofthewild.register;

import io.github.ender.towersofthewild.TowersOfTheWild;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.registry.DynamicRegistryManager;

public class JigsawRegistration {

	public static StructurePool register(StructurePool pattern) {
		TowersOfTheWild.LOGGER.info(pattern.getId().getPath() + " pattern registered.");
		return StructurePools.register(pattern);
	}

	public static void registerPostSetup(StructurePool pattern, DynamicRegistryManager registries) {
		register(pattern);
	}
}
