package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.SD_REMIX;

public class SDRemixPower extends AbstractPower {
    private static final String POWER_ID = SD_REMIX;
    private static final String NAME = "SD Remix";

    public SDRemixPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1){
            this.description = "For this turn, upgrade the cards that are played.";
        } else {
            this.description = "For the next " + this.amount + " turns, upgrade the cards that are played.";
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard c) {
        if (c.canUpgrade()) {
            c.upgrade();
            c.superFlash();
            c.applyPowers();
            this.flash();
        }
    }

    @Override
    public void atEndOfRound() {
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    }
}
