package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.ANGEL_PLATFORM;
import static globals.Constants.Powers.PERCENT;

public class PercentPower extends AbstractPower {
    public static final String POWER_ID = PERCENT;
    public static final String NAME = "Percent";
    public PercentPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        this.description = "Takes " + this.amount + "% more damage from attacks";
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return (float) (damage * (1 + (this.amount / 100.0)));
        } else {
            return damage;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount >= 100 && !this.owner.hasPower(ANGEL_PLATFORM)) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new AngelPlatformPower(this.owner, 1)));
        }
    }
}
