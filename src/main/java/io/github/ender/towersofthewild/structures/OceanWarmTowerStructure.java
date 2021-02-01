package io.github.ender.towersofthewild.structures;

import com.mojang.serialization.Codec;
import io.github.ender.towersofthewild.config.TowersOfTheWildConfig;
import io.github.ender.towersofthewild.structures.pieces.OceanWarmTowerPools;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class OceanWarmTowerStructure extends AbstractTowerStructure {

	public OceanWarmTowerStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public int getSeedModifier() {
		return 1689782;
	}

	@Override
	public int getSize() {
		return 7;
	}

	@Override
	public int getDistance() {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.oceanRarity;
	}

	@Override
	public int getSeparation() {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.oceanRarity / 3;
	}

	// can generate
	@Override
	protected boolean shouldStartAt(ChunkGenerator generator, BiomeSource biomeProvider, long seed, ChunkRandom rand, int chunkX, int chunkZ, Biome biome, ChunkPos pos, StructurePoolFeatureConfig config) {
		if (isTerrainFlat(generator, chunkX, chunkZ)) {
			if (!alreadyIsTower(generator, this, seed, rand, chunkX, chunkZ)) {

				int xStart = chunkX * 16;
				int zStart = chunkZ * 16;
				int startHeight = generator.getHeight(xStart, zStart, Heightmap.Type.OCEAN_FLOOR_WG);
				return startHeight <= 38;
			}
		}
		return false;
	}

	@Override
	protected boolean alreadyIsTower(ChunkGenerator generator, AbstractTowerStructure structure, long seed, ChunkRandom rand, int chunkX, int chunkZ) {
		return false;
	}


	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory()  {
		return OceanWarmTowerStructure.Start::new;
	}

	public static class Start extends MarginedStructureStart<StructurePoolFeatureConfig> {
		public Start(StructureFeature<StructurePoolFeatureConfig> structure, int p_i225876_2_, int p_i225876_3_, BlockBox boundingBox, int p_i225876_5_, long p_i225876_6_) {
			super(structure, p_i225876_2_, p_i225876_3_, boundingBox, p_i225876_5_, p_i225876_6_);
		}

		// generate
		public void init(DynamicRegistryManager registries, ChunkGenerator generator, StructureManager manager, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, StructurePoolFeatureConfig StructurePoolFeatureConfig) {
			int i = p_230364_4_ * 16;
			int j = p_230364_5_ * 16;
			if (!OceanWarmTowerPools.registered) {
				OceanWarmTowerPools.init(registries);
			}
			BlockPos blockpos = new BlockPos(i, generator.getHeight(i, j, Heightmap.Type.OCEAN_FLOOR_WG) - generator.getHeight(i, j, Heightmap.Type.WORLD_SURFACE_WG), j);
			StructurePoolBasedGenerator.method_30419(registries, StructurePoolFeatureConfig, PoolStructurePiece::new, generator, manager, blockpos, this.children, this.random, true, true);
			this.setBoundingBoxFromChildren();
		}
	}
}
