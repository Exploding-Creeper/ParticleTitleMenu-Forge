package morningsage.particletitlescreen;

import morningsage.particletitlescreen.config.ConfigFileHandler;
import morningsage.particletitlescreen.config.ModConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static morningsage.particletitlescreen.ParticleTitleScreen.MOD_ID;

@Mod(MOD_ID)
public class ParticleTitleScreen {

	public static final String MOD_ID = "particletitlescreen";
	public static final ConfigFileHandler configFileHandler = new ConfigFileHandler(ModConfig.class, MOD_ID);
	public static ParticleScreenManager particleScreenManager;

	public ParticleTitleScreen() {

	}

}
