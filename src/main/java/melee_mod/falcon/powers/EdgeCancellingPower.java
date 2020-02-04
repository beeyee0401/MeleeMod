package falcon_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.CustomTags;

import java.util.ArrayList;

import static globals.Constants.Powers.EDGE_CANCELLING;

public class EdgeCancellingPower extends AbstractPower {
    public static final String POWER_ID = EDGE_CANCELLING;
    public static final String NAME = "Edge Cancelling";
    public static final String DESCRIPTION = "Aerials cost 0 for the next ";

    public EdgeCancellingPower(AbstractCreature owner, int amount) {
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
        this.description = this.amount > 1 ? "Aerials cost 1 less [E] for the next " + this.amount + " turns." : "Aerials cost 1 less [E] for this turn.";
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

    @Override
    public void onInitialApplication() {
        setCardCosts(AbstractDungeon.player.hand.group);
        setCardCosts(AbstractDungeon.player.drawPile.group);
        setCardCosts(AbstractDungeon.player.discardPile.group);
        setCardCosts(AbstractDungeon.player.exhaustPile.group);
    }

    private void setCardCosts(ArrayList<AbstractCard> cards){
        for (AbstractCard c : cards) {
            if (c.tags.contains(CustomTags.AERIAL)) {
                c.costForTurn = c.cost - 1;
                c.isCostModifiedForTurn = true;
            }
        }
    }
}