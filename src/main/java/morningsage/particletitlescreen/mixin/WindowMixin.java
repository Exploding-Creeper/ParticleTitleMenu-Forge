package morningsage.particletitlescreen.mixin;

import morningsage.particletitlescreen.ParticleTitleScreen;
import net.minecraft.client.MainWindow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainWindow.class)
@OnlyIn(Dist.CLIENT)
public class WindowMixin {
    @Inject(
        at = @At("RETURN"),
        method = "onEnter"
    )
    private void onCursorEnterChanged(long window, boolean entered, CallbackInfo callbackInfo) {
        if (!entered && ParticleTitleScreen.particleScreenManager != null) {
            ParticleTitleScreen.particleScreenManager.onMouseLeave();
        }
    }
}
