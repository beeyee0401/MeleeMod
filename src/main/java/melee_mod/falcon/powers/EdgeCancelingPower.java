package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Enums;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.helpers.CardCostHelper;

import static globals.Constants.Powers.EDGE_CANCELING;

public class EdgeCancelingPower extends AbstractPower {
    public static final String POWER_ID = EDGE_CANCELING;
    public static final String NAME = "Edge Canceling";

    public EdgeCancelingPower(AbstractCreature owner, int amount) {
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
        if (this.amount == 1){
            CardCostHelper.resetCardCosts(AbstractDungeon.player.hand.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.drawPile.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.discardPile.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.exhaustPile.group);
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

    @Override
    public void onInitialApplication() {
        int reduction = 1;
        CardCostHelper.setCardCosts(AbstractDungeon.player.hand.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.drawPile.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.discardPile.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.exhaustPile.group,Enums.CostAction.REDUCE, reduction);
    }
}