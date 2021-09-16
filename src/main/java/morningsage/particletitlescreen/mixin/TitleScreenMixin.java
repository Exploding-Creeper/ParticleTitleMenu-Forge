package morningsage.particletitlescreen.mixin;

import morningsage.particletitlescreen.ParticleScreenManager;
import morningsage.particletitlescreen.ParticleTitleScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenuScreen.class)
@OnlyIn(Dist.CLIENT)
public class TitleScreenMixin {
    @Inject(
        at = @At("RETURN"),
        method = "<init>(Z)V"
    )
    public void init(boolean doBackgroundFade, CallbackInfo callbackInfo) {
        ParticleTitleScreen.particleScreenManager = new ParticleScreenManager();
    }
}
