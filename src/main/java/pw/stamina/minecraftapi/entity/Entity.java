/*
 * MIT License
 *
 * Copyright (c) 2017 Stamina Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pw.stamina.minecraftapi.entity;

import pw.stamina.minecraftapi.util.BoundingBox;
import pw.stamina.minecraftapi.util.Rotation;

import java.util.UUID;

public interface Entity {

    int getEntityId();
    UUID getUniqueId();

    Entity getRider();

    double posX();
    void posX(double posX);

    double posY();
    void posY(double posY);

    double posZ();
    void posZ(double posZ);

    double prevPosX();
    double prevPosY();
    double prevPosZ();

    double motionX();
    void motionX(double motionX);

    double motionY();
    void motionY(double motionY);

    double motionZ();
    void motionZ(double motionZ);

    float yaw();
    void yaw(float yaw);

    float pitch();
    void pitch(float pitch);

    default Rotation getRotation() {
        return Rotation.from(yaw(), pitch());
    }

    default void setRotation(Rotation rotation) {
        yaw(rotation.getYaw());
        pitch(rotation.getPitch());
    }

    BoundingBox getBoundingBox();

    boolean onGround();
    void onGround(boolean onGround);

    boolean isDead();

    float width();
    float height();

    float getEyeHeight();

    boolean isNoClipping();
    void setNoClipping(boolean noClipping);

    int getTicksExisted();

    float getDistanceToEntity(Entity other);
}
