package io.github.ender.towersofthewild.structures;

import com.mojang.serialization.Codec;
import io.github.ender.towersofthewild.config.TowersOfTheWildConfig;
import io.github.ender.towersofthewild.register.TowerStructuresRegistry;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;

import java.util.Objects;

public abstract class AbstractTowerStructure extends JigsawFeature {

	public AbstractTowerStructure (Codec<StructurePoolFeatureConfig> codec) {
		super(codec, 0, true, true);
	}

	public int getDistance () {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.rarity;
	}

	public int getSeparation () {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.rarity / 3;
	}

	public abstract int getSeedModifier ();

	public abstract int getSize ();

	private String getNamespace(Biome biome) {
		Identifier identifier = BuiltinRegistries.BIOME.getId(biome);
		if (identifier != null) {
			return identifier.getNamespace();
		}
		return "";
	}

	@Override
	protected boolean shouldStartAt (ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, StructurePoolFeatureConfig config) {
		if (isTerrainFlat(chunkGenerator, chunkX, chunkZ)) {
			if (!alreadyIsTower(chunkGenerator, this, worldSeed, random, chunkX, chunkZ)) {
				// Check the entire size of the structure for Blacklisted Biomes
				for(Biome biome1 : biomeSource.getBiomesInArea(chunkX * 16 + getSize() / 2, chunkGenerator.getSeaLevel(), chunkZ * 16 + getSize() / 2, getSize() * 16)) {
					if (biome1.toString() != null) {
						TowersOfTheWildConfig totwConfig = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
						if (totwConfig.biomeBlackList.contains(biome1.toString())
							    || totwConfig.allModBiomesBlackList.contains(getNamespace(biome1))) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	protected boolean alreadyIsTower(ChunkGenerator generator, AbstractTowerStructure structure, long seed, ChunkRandom rand, int chunkX, int chunkZ) {
		for (int k = chunkX - getSeparation() / 2; k <= chunkX + getSeparation() / 2; ++k) {
			for (int l = chunkZ - getSeparation() / 2; l <= chunkZ + getSeparation() / 2; ++l) {
				for (StructureFeature<?> ro : TowerStructuresRegistry.STRUCTURE_FEATURES) {
					if (!structure.equals(ro)) {
						ChunkPos otherStructurePos = ro.getStartChunk(Objects.requireNonNull(generator.getStructuresConfig().getForType(ro)), seed, rand, k, l);
						if (k == otherStructurePos.x && l == otherStructurePos.z) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	protected boolean isTerrainFlat(ChunkGenerator generator, int chunkX, int chunkZ) {
		// Size of the area to check.
		int offset = getSize();

		int xStart = chunkX * 16;
		int zStart = chunkZ * 16;

		int i1 = generator.getHeight(xStart, zStart, Heightmap.Type.WORLD_SURFACE_WG);
		int j1 = generator.getHeight(xStart, zStart + offset, Heightmap.Type.WORLD_SURFACE_WG);
		int k1 = generator.getHeight(xStart + offset, zStart, Heightmap.Type.WORLD_SURFACE_WG);
		int l1 = generator.getHeight(xStart + offset, zStart + offset, Heightmap.Type.WORLD_SURFACE_WG);
		int minHeight = Math.min(Math.min(i1, j1), Math.min(k1, l1));
		int maxHeight = Math.max(Math.max(i1, j1), Math.max(k1, l1));

		return Math.abs(maxHeight - minHeight) <= 4;
	}

	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
		return AbstractTowerStructure.Start::new;
	}


	public static class Start extends MarginedStructureStart<StructurePoolFeatureConfig> {
		public Start(StructureFeature<StructurePoolFeatureConfig> structure, int p_i225876_2_, int p_i225876_3_, BlockBox boundingBox, int p_i225876_5_, long p_i225876_6_) {
			super(structure, p_i225876_2_, p_i225876_3_, boundingBox, p_i225876_5_, p_i225876_6_);
		}

		// generate
		public void init(DynamicRegistryManager registryManager, ChunkGenerator generator, StructureManager manager, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, StructurePoolFeatureConfig featureConfig) {
			int i = p_230364_4_ * 16;
			int j = p_230364_5_ * 16;
			BlockPos blockpos = new BlockPos(i, 0, j);
			StructurePoolBasedGenerator.method_30419(registryManager, featureConfig, PoolStructurePiece::new, generator, manager, blockpos, this.children, this.random, true, true);
			this.setBoundingBoxFromChildren();
		}
	}
}