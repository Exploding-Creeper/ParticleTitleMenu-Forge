package morningsage.particletitlescreen;

import lombok.var;
import morningsage.particletitlescreen.config.ModConfig;
import morningsage.particletitlescreen.utils.RandomUtils;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static morningsage.particletitlescreen.config.ModConfig.*;

public class ParticleScreenManager {
    private final List<Particle> particles = new ArrayList<>();
    private static final Vector2f ZERO = Vector2f.ZERO;
    private @Nullable Vector2f mouseLocation = null;
    private @Nullable Vector2f resolution = null;
    private @Nullable Double scale = null;
    private static final MainWindow window = Minecraft.getInstance().getWindow();

    public ParticleScreenManager() {
        initParticles();
        initMouseEvents();

    }

    public void initParticles() {
        particles.clear();

        int particleColor = Integer.decode(ModConfig.particleColor);
        double particleCount = window.getWidth() * window.getHeight() / window.getGuiScale() / 4000;

        for (int i = 0; i < particleCount; i++) {
            var particleBuilder = Particle.builder();

            if (randomParticleRadius) {
                particleBuilder.radius(RandomUtils.getRandomFloat(particleMinRadius, particleMaxRadius));
            } else {
                particleBuilder.radius(particleRadius);
            }

            if (randomParticleOpacity) {
                particleBuilder.opacity(RandomUtils.getRandomFloat(particleMinOpacity, particleMaxOpacity));
            } else {
                particleBuilder.opacity(particleOpacity);
            }

            if (particleMovement) {
                particleBuilder.speed(particleMovementSpeed);
            }

            particleBuilder.color(particleColor);
            particleBuilder.locationVec(RandomUtils.getRandomLocation());
            particleBuilder.baseVelocity(ZERO);

            particles.add(particleBuilder.build());
        }
    }
    private void initMouseEvents() {
        if (!particleRepelledByMouse) return;
    }

    public void onMouseLeave() {
        mouseLocation = null;
    }

    public void onMouseMove(double x, double y) {
        mouseLocation = new Vector2f((float) x, (float) y);
    }

    public ActionResultType onRender() {
        onRenderBackground();
        onRenderParticles();

        if (resolution == null || resolution.x != window.getGuiScaledWidth() || resolution.y != window.getGuiScaledHeight()) {
            resolution = new Vector2f(window.getGuiScaledWidth(), window.getGuiScaledHeight());
        }

        if (scale == null || scale != window.getGuiScale()) {
            scale = window.getGuiScale();
        }

        return ActionResultType.CONSUME;
    }
    private static void onRenderBackground() {
        int backgroundColor = Integer.decode(ModConfig.backgroundColor);

        final float red   = (float)(backgroundColor >> 16 & 255) / 255.0F;
        final float green = (float)(backgroundColor >>  8 & 255) / 255.0F;
        final float blue  = (float)(backgroundColor       & 255) / 255.0F;

        int width  = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        GL11.glColor3f(red, green, blue);

        GL11.glBegin(GL11.GL_QUAD_STRIP);
        GL11.glVertex2i(0, 0);
        GL11.glVertex2i(0, height);
        GL11.glVertex2i(width, 0);
        GL11.glVertex2i(width, height);
        GL11.glEnd();
    }
    private void onRenderParticles() {
        // Determine position first to make sure all the interactions match up
        if (particleMovement) {
            Vector2f windowSize = new Vector2f(window.getGuiScaledHeight(), window.getGuiScaledWidth());

            for (Particle particle : particles) {
                particle.move();
                RandomUtils.moveParticleIfNeeded(particle, particleBounce);
                particle.interactWithMouse(mouseLocation, windowSize, particleBounce, particleDistanceRepelledByMouse, (float) window.getGuiScale());
            }
        }

        int particleInteractionColor = particleInteractions ? Integer.decode(ModConfig.particleInteractionColor) : -1;

        // Then draw everything
        for (int i = 0; i < particles.size(); i++) {
            Particle particle1 = particles.get(i);

            particle1.draw();

            if (particleInteractions) {
                for (int x = i + 1; x < particles.size(); x++) {
                    particle1.interact(
                        particles.get(x),
                        particleInteractionDistance,
                        particleInteractionOpacity,
                        particleInteractionColor
                    );
                }
            }
        }
    }
}
