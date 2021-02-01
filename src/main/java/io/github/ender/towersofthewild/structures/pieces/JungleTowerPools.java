package io.github.ender.towersofthewild.structures.pieces;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.ender.towersofthewild.TowersOfTheWild;
import io.github.ender.towersofthewild.register.JigsawRegistration;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;

public class JungleTowerPools {
	public static boolean registered = false;

	public static void init(DynamicRegistryManager registryManager) {
		JigsawRegistration.registerPostSetup(TOP, registryManager);
	}

	public static StructurePool BOTTOM = JigsawRegistration.register(
		new StructurePool(
			new Identifier(TowersOfTheWild.MOD_ID, "jungle_bottom"),
			new Identifier("empty"),
			ImmutableList.of(
				Pair.of(StructurePoolElement.method_30426(TowersOfTheWild.MOD_ID + ":jungle/jungle_tower_bottom", StructureProcessorLists.EMPTY), 1)),
			StructurePool.Projection.RIGID)
	);

	public static StructurePool TOP =
		new StructurePool(
			new Identifier(TowersOfTheWild.MOD_ID, "jungle_top"),
			new Identifier("empty"),
			ImmutableList.of(
				Pair.of(StructurePoolElement.method_30426(
						TowersOfTheWild.MOD_ID + ":jungle/jungle_tower_top",
					StructureProcessorLists.EMPTY), 1)),
			StructurePool.Projection.RIGID);
}
