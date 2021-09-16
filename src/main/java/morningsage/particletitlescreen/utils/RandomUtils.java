package morningsage.particletitlescreen.utils;

import morningsage.particletitlescreen.Particle;
import morningsage.particletitlescreen.ducks.Vec2fDuck;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector2f;

import java.util.Random;

public final class RandomUtils {
    private static final Random random = new Random();
    private static final MainWindow minecraftWindow = Minecraft.getInstance().getWindow();

    public static float getRandomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
    public static float getRandomFloat() {
        return getRandomFloat(0, 1);
    }

    private static float getRandomX() {
        return getRandomFloat(0, minecraftWindow.getGuiScaledWidth());
    }

    private static float getRandomY() {
        return getRandomFloat(0, minecraftWindow.getGuiScaledHeight());
    }

    public static Vector2f getRandomLocation() {
        return new Vector2f(getRandomX(), getRandomY());
    }

    public static void moveParticleIfNeeded(Particle particle, boolean bounce) {
        int width  = minecraftWindow.getGuiScaledWidth();
        int height = minecraftWindow.getGuiScaledWidth();

        Vec2fDuck location = (Vec2fDuck) particle.getLocationVec();
        float radius   = particle.getRadius();

        if (bounce) {
            Vec2fDuck velocity = (Vec2fDuck) particle.getRealizedVelocity();

            if (location.getX() + radius > width || location.getX() - radius < 0) {
                velocity.setX(-velocity.getX());
            }

            if (location.getY() + radius > height || location.getY() - radius < 0) {
                velocity.setY(-velocity.getY());
            }
        } else {
            if (location.getX() - radius > width) {
                location.set(-radius, getRandomY());
            } else if (location.getX() + radius < 0) {
                location.set(width + radius, getRandomY());
            }

            if (location.getY() - radius > height) {
                location.set(getRandomX(), -radius);
            } else if (location.getY() + radius < 0) {
                location.set(getRandomX(), height + radius);
            }
        }
    }
}
