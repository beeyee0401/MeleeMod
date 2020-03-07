package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.CAUTIOUS;

public class CautiousPower extends AbstractPower {
    private static final String POWER_ID = CAUTIOUS;
    private static final String NAME = "Cautious";

    public CautiousPower(AbstractCreature owner, int amount) {
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
        this.description = "Whenever you apply a combo-point, gain " + this.amount + " Block.";
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(Constants.Powers.COMBO_POINTS)){
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.amount * power.amount));
        }
    }
}
