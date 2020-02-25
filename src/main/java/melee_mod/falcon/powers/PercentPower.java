package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.ANGEL_PLATFORM;
import static globals.Constants.Powers.PERCENT;

public class PercentPower extends AbstractPower {
    private static final String POWER_ID = PERCENT;
    private static final String NAME = "Percent";

    public PercentPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        AbstractPlayer p = AbstractDungeon.player;
        if (!this.owner.isPlayer && p.hasRelic(Constants.Relics.MARTHRITIS)){
            p.getRelic(Constants.Relics.MARTHRITIS).flash();
            this.amount *= 2;
        }
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        this.description = "Takes " + this.amount + "% more damage from attacks. At 100%, at the end of the turn, gain 1 Intangible and remove all %";
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return (float) Math.round(damage * (1 + (this.amount / 100.0)));
        } else {
            return damage;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 100 && !this.owner.hasPower(ANGEL_PLATFORM)) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new AngelPlatformPower(this.owner, 1)));
        }
    }
}
