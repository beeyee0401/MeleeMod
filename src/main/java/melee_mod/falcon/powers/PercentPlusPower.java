package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.PERCENT_PLUS;

public class PercentPlusPower extends AbstractPower {
    private static final String POWER_ID = PERCENT_PLUS;
    private static final String NAME = "Racking Up Percent";

    public PercentPlusPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.canGoNegative = false;
    }

    @Override
    public void updateDescription() {
        this.description = "At the start of your turn, gain " + this.amount + " Percent.";
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new PercentPower(this.owner, this.amount)));
    }
}
