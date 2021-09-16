package morningsage.particletitlescreen.mixin;

import morningsage.particletitlescreen.ParticleTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(
        at = @At("RETURN"),
        method = "resizeDisplay"
    )
    public void onResolutionChanged(CallbackInfo callbackInfo) {

        if (ParticleTitleScreen.particleScreenManager != null) {
            ParticleTitleScreen.particleScreenManager.initParticles();
        }

    }
}
