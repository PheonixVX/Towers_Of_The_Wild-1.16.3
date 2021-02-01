package io.github.ender.towersofthewild.structures;

import com.mojang.serialization.Codec;
import io.github.ender.towersofthewild.structures.pieces.JungleTowerPools;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class JungleTowerStructure extends AbstractTowerStructure {
	public JungleTowerStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public int getSeedModifier() {
		return 1689778;
	}

	@Override
	public int getSize() {
		return 7;
	}

	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
		return JungleTowerStructure.Start::new;
	}

	public static class Start extends MarginedStructureStart<StructurePoolFeatureConfig> {
		public Start(StructureFeature<StructurePoolFeatureConfig> structure, int p_i225876_2_, int p_i225876_3_, BlockBox boundingBox, int p_i225876_5_, long p_i225876_6_) {
			super(structure, p_i225876_2_, p_i225876_3_, boundingBox, p_i225876_5_, p_i225876_6_);
		}

		// generate
		public void init(DynamicRegistryManager registries, ChunkGenerator generator, StructureManager manager, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, StructurePoolFeatureConfig villageConfig) {
			int i = p_230364_4_ * 16;
			int j = p_230364_5_ * 16;
			if (!JungleTowerPools.registered) {
				JungleTowerPools.init(registries);
			}
			BlockPos blockpos = new BlockPos(i, 0, j);
			StructurePoolBasedGenerator.method_30419(registries, villageConfig, PoolStructurePiece::new, generator, manager, blockpos, this.children, this.random, true, true);
			this.setBoundingBoxFromChildren();

		}
	}
}
