package morningsage.particletitlescreen.ducks;

import net.minecraft.util.math.vector.Vector2f;

public interface Vec2fDuck {
    void set(float x, float y);
    void setX(float x);
    float getX();
    void setY(float y);
    float getY();

    double distance(Vector2f v);
    Vector2f multiply(float factor);
    Vector2f add(Vector2f addend);
}
