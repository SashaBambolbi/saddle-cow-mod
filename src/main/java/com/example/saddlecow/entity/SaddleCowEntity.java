package com.example.saddlecow.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.CompoundTag;

public class SaddleCowEntity extends Cow {
    private boolean saddled = false;

    public SaddleCowEntity(EntityType<? extends Cow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isSaddled() && itemstack.is(Items.SADDLE)) {
            this.setSaddled(true);
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.playSound(net.minecraft.sounds.SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (this.isSaddled() && itemstack.is(Items.WHEAT) && !this.isVehicle()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
                if (this.isControlledByLocalInstance()) {
                    float f = player.xxa;
                    float f1 = player.zza;
                    this.setSpeed(0.4f);
                    super.travel(new Vec3(f, travelVector.y, f1));
                } else {
                    super.travel(travelVector);
                }
            } else {
                super.travel(travelVector);
            }
        }
    }

    @Override
    public Player getControllingPassenger() {
        if (this.getFirstPassenger() instanceof Player player) {
            return player;
        }
        return null;
    }

    @Override
    protected boolean canAddPassenger(net.minecraft.world.entity.Entity passenger) {
        return this.getPassengers().size() < 1;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddled", this.saddled);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddled"));
    }

    public boolean isSaddled() {
        return this.saddled;
    }

    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }
}
