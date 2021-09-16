package morningsage.particletitlescreen.mixin;

import morningsage.particletitlescreen.ParticleTitleScreen;
import net.minecraft.client.MouseHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHelper.class)
@OnlyIn(Dist.CLIENT)
public class MouseMixin {
    @Inject(
        at = @At("RETURN"),
        method = "onMove"
    )
    private void onCursorPos(long window, double x, double y, CallbackInfo callbackInfo) {

        if (ParticleTitleScreen.particleScreenManager != null) {
            ParticleTitleScreen.particleScreenManager.onMouseMove(x, y);
        }

    }
}
