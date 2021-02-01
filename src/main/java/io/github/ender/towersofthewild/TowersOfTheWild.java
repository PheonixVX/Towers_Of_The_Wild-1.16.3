package io.github.ender.towersofthewild;

import io.github.ender.towersofthewild.config.TowersOfTheWildConfig;
import io.github.ender.towersofthewild.setup.WorldSetup;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TowersOfTheWild implements ModInitializer {

	public static final String MOD_ID = "towers_of_the_wild";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize () {
		// Config stuff
		AutoConfig.register(TowersOfTheWildConfig.class, Toml4jConfigSerializer::new);

		WorldSetup.setup();
	}
}
