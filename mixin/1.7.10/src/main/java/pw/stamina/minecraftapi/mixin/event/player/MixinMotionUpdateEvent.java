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

package pw.stamina.minecraftapi.mixin.event.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pw.stamina.minecraftapi.MinecraftApi;
import pw.stamina.minecraftapi.entity.living.ClientPlayer;
import pw.stamina.minecraftapi.event.player.MotionUpdateEvent;
import pw.stamina.minecraftapi.network.NetHandlerPlayClient;

@Mixin(EntityClientPlayerMP.class)
public abstract class MixinMotionUpdateEvent extends EntityPlayerSP {

    @Shadow @Final
    public net.minecraft.client.network.NetHandlerPlayClient sendQueue;

    @Shadow private double oldPosX;
    @Shadow private double oldPosY;
    @Shadow private double oldPosZ;
    @Shadow private float oldRotationYaw;
    @Shadow private float oldRotationPitch;
    @Shadow private boolean wasSneaking;
    @Shadow private boolean wasSprinting;

    // MotionUpdateEvent
    private double originalX;
    private double originalZ;

    private float originalYaw;
    private float originalPitch;
    private boolean originalOnGround;

    private MotionUpdateEvent event;

    public MixinMotionUpdateEvent(Minecraft p_i1238_1_, World p_i1238_2_, Session p_i1238_3_, int p_i1238_4_) {
        super(p_i1238_1_, p_i1238_2_, p_i1238_3_, p_i1238_4_);
    }

    @Inject(method = "sendMotionUpdates", cancellable = true, at = @At("HEAD"))
    public void emitMotionUpdateAndModifyPlayerState(CallbackInfo cbi) {
        storeOriginalState();

        event = createMotionUpdateEvent();
        MinecraftApi.emitEvent(event);

        if (event.isCancelled()) {
            cbi.cancel();
        }

        modifyPlayerState();
    }

    private void storeOriginalState() {
        originalX = posX;
        originalZ = posZ;

        originalYaw = rotationYaw;
        originalPitch = rotationPitch;
        originalOnGround = onGround;
    }

    private MotionUpdateEvent createMotionUpdateEvent() {
        return new MotionUpdateEvent(
                (ClientPlayer) this,
                posX, posY, posZ,
                oldPosX, oldPosY, oldPosZ,

                rotationYaw, rotationPitch,
                oldRotationYaw, oldRotationPitch,

                isSprinting(), wasSprinting,
                isSneaking(), wasSneaking,
                onGround,

                (NetHandlerPlayClient) sendQueue);
    }

    private void modifyPlayerState() {
        posX = event.getX();
        posZ = event.getZ();
        boundingBox.minY += event.getOffsetY();

        rotationYaw = event.getYaw();
        rotationPitch = event.getPitch();
        onGround = event.isOnGround();
    }

    @Redirect(method = "sendMotionUpdates",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    public boolean redirectIsSneaking(Entity entity) {
        return event.isSneaking();
    }

    @Redirect(method = "sendMotionUpdates",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSprinting()Z"))
    public boolean redirectIsSprinting(Entity entity) {
        return event.isSneaking();
    }

    @Inject(method = "sendMotionUpdates", at = @At("TAIL"))
    public void restorePlayerStateAfterMotionUpdateEvent(CallbackInfo cbi) {
        posX = originalX;
        posZ = originalZ;
        boundingBox.minY -= event.getOffsetY();

        rotationYaw = originalYaw;
        rotationPitch = originalPitch;
        onGround = originalOnGround;

        event.sendPackets(((NetHandlerPlayClient) sendQueue)::queuePacket);
    }
}
