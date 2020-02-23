package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.WE_TECH_THOSE;

public class WeTechThosePower extends AbstractPower {
    private static final String POWER_ID = WE_TECH_THOSE;
    private static final String NAME = "We Tech Those";

    public WeTechThosePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "If you take 5 or more damage, gain Dexterity equal to " + (this.amount * 10) + "% of the damage that expires at the end of the next turn.";
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount >= 5){
            int amount = (int) Math.round(damageAmount * (this.amount / 10.0));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, amount)));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new LoseDexterityPower(this.owner, amount)));
        }
        return damageAmount;
    }
}
