package morningsage.particletitlescreen.mixin;

import lombok.Getter;
import lombok.Setter;
import morningsage.particletitlescreen.ducks.Vec2fDuck;
import net.minecraft.util.math.vector.Vector2f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vector2f.class)
public class Vec2fMixin implements Vec2fDuck {
    @Getter @Setter @Mutable @Shadow @Final public float x;
    @Getter @Setter @Mutable @Shadow @Final public float y;

    @Override
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double distance(Vector2f v) {
        float vx = v.x - this.x;
        float vy = v.y - this.y;

        return Math.sqrt(vx * vx + vy * vy);
    }

    @Override
    public Vector2f multiply(float factor) {
        return new Vector2f(this.x * factor, this.y * factor);
    }

    @Override
    public Vector2f add(Vector2f addend) {
        return new Vector2f(this.x + addend.x, this.y + addend.y);
    }
}
