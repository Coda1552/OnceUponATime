package codyhuh.onceuponatime.common.entities.goal;

import codyhuh.onceuponatime.common.entities.Hippogryph;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class HippogryphLandOnGroundGoal extends WaterAvoidingRandomStrollGoal {
    private final Hippogryph mob;

    public HippogryphLandOnGroundGoal(Hippogryph goalOwner, double speedMod) {
        super(goalOwner, speedMod, 1);
        this.mob = goalOwner;
    }

    @Override
    public void start() {
        super.start();
        mob.setFlying(false);
        mob.setLanding(true);
    }

    @Override
    public boolean canUse() {
        return mob.isFlying() && !mob.wantsToFly() && super.canUse();
    }

    public void trigger() {
        forceTrigger = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (mob.isLanding() && mob.onGround()) {
            mob.setLanding(false);
        }

    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 32, 24) : super.getPosition();
    }

    @Override
    public void stop() {
        mob.setLanding(false);
    }
}
