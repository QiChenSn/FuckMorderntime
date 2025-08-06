package com.qichen.mixin;

import com.qichen.command.FuckEggCommand;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends ZombieEntity implements Angerable{

     @Unique
     private static boolean State=false;

     public  boolean getState(){
         return State;
     }

    public  void setState(boolean val){
        State=val;
    }

    PrioritizedGoal oldGoal=null;

    public ZombifiedPiglinEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "mobTick",at=@At("TAIL"))
    public void FuckTick(ServerWorld world, CallbackInfo ci){
        State= FuckEggCommand.isEnabled;
        Set<PrioritizedGoal> goals = this.goalSelector.getGoals();
        if(!State&&oldGoal!=null){
            goals.add(oldGoal);
        }
        goals.stream().forEach(
                (pg)->{
                    if(State&&pg.getGoal().toString().equals("DestroyEggGoal")){
                        oldGoal=pg;
                        goals.remove(pg);
                    }
                }
        );
    }
}