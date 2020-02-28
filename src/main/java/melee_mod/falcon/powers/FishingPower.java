package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.actions.RetainKeywordCardAction;

import static globals.Constants.Powers.FISHING;

public class FishingPower extends AbstractPower {
    private static final String POWER_ID = FISHING;
    private static final String NAME = "Fishing";

    public FishingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.isTurnBased = true;
        this.canGoNegative = false;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = "At the end of your turn, retain up to 1 Finisher card.";
        } else {
            this.description = "At the end of your turn, retain up to " + this.amount + " Finisher cards.";
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic(RunicPyramid.ID) && !AbstractDungeon.player.hasPower("Equilibrium")) {
            this.addToBot(new RetainKeywordCardAction(this.owner, this.amount, Constants.Keywords.FINISHER));
        }
    }
}
