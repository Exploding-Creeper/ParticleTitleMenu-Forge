package morningsage.particletitlescreen.mixin;

import morningsage.particletitlescreen.ParticleTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSkyboxCube.class)
@OnlyIn(Dist.CLIENT)
public abstract class RotatingCubeMapRendererMixin {
	@Inject(
		at = @At("HEAD"),
		method = "render",
		cancellable = true
	)
	public void render(Minecraft p_217616_1_, float p_217616_2_, float p_217616_3_, float p_217616_4_, CallbackInfo ci) {

		if (ParticleTitleScreen.particleScreenManager != null) {
			ActionResultType result = ParticleTitleScreen.particleScreenManager.onRender();
			if (result != ActionResultType.PASS) ci.cancel();
		}

	}
}
