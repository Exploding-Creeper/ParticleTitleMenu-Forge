package morningsage.particletitlescreen;

import lombok.*;
import morningsage.particletitlescreen.ducks.Vec2fDuck;
import morningsage.particletitlescreen.utils.RandomUtils;
import morningsage.particletitlescreen.utils.RenderUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.Nullable;

@Data @Builder
public class Particle {

    @NonNull Vector2f locationVec;
    @Builder.Default float opacity = 1.0F;
    @Builder.Default int color = 0xFFFFFFFF;
    @Builder.Default float radius = 5.0F;
    @Builder.Default Vector2f baseVelocity = Vector2f.ZERO;
    @Builder.Default float speed = 0.0F;

    Vector2f realizedVelocity;

    public void interact(Particle particle, double maximumDistance, float maxOpacity, int color) {
        double dist = ((Vec2fDuck) locationVec).distance(particle.getLocationVec());

        if (dist > maximumDistance) return;

        RenderUtils.renderLine(
            locationVec, particle.locationVec,
            1.0F, color,
            (float)(maxOpacity - dist / (1 / maxOpacity) / maximumDistance)
        );
    }

    public void draw() {
        RenderUtils.renderPoint(locationVec, radius, color, opacity);
    }

    public void move() {
        if (realizedVelocity == null) {
            realizedVelocity = new Vector2f(
                baseVelocity.x + RandomUtils.getRandomFloat() - 0.5F,
                baseVelocity.y + RandomUtils.getRandomFloat() - 0.5F
            );
        }

        float ms = speed / 2.0F;

        ((Vec2fDuck) locationVec).set(locationVec.x + realizedVelocity.x * ms, locationVec.y + realizedVelocity.y * ms);
    }
    public void interactWithMouse(@Nullable Vector2f mouse, Vector2f windowSize, boolean bounce, float repelledRadius, float scale) {
        if (mouse == null) return;

        float scaledRepelledRadius = -10.0F * scale + repelledRadius;

        Vector2f tmp = new Vector2f(locationVec.x - mouse.x / scale, locationVec.y - mouse.y / scale);
        float dist_mouse = (float) Math.sqrt(tmp.x * tmp.x + tmp.y * tmp.y);

        Vector2f normVec = new Vector2f(tmp.x / dist_mouse, tmp.y / dist_mouse);
        float repelFactor = (float) MathHelper.clamp((1 / scaledRepelledRadius) * (-1 * Math.pow(dist_mouse / scaledRepelledRadius, 2) + 1) * scaledRepelledRadius * 100, 0, 50);

        Vector2f newPosition = ((Vec2fDuck) locationVec).add(((Vec2fDuck) normVec).multiply(repelFactor));

        if (bounce) {
            if (newPosition.x - radius > 0 && newPosition.x + radius < windowSize.x) ((Vec2fDuck) locationVec).setX(newPosition.x);
            if (newPosition.y - radius > 0 && newPosition.y + radius < windowSize.y) ((Vec2fDuck) locationVec).setY(newPosition.y);
        } else {
            ((Vec2fDuck) locationVec).set(newPosition.x, newPosition.y);
        }
    }
}


