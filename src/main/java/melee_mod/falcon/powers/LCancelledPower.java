package falcon_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.CustomTags;

import java.util.ArrayList;

import static globals.Constants.Powers.LCANCELED;

public class LCancelledPower extends AbstractPower {
    private static final String POWER_ID = LCANCELED;
    private static final String NAME = "Just L-Canceled";
    private static final String DESCRIPTION = "Cost of your next non-Aerial card is reduced by 1 [E]";

    private enum CostAction {
        REDUCE,
        RESET
    }

    public LCancelledPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.tags.contains(CustomTags.AERIAL)){
            setCardCosts(AbstractDungeon.player.hand.group, CostAction.RESET);
            setCardCosts(AbstractDungeon.player.drawPile.group, CostAction.RESET);
            setCardCosts(AbstractDungeon.player.discardPile.group, CostAction.RESET);
            setCardCosts(AbstractDungeon.player.exhaustPile.group, CostAction.RESET);
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        setCardCosts(AbstractDungeon.player.hand.group, CostAction.REDUCE);
        setCardCosts(AbstractDungeon.player.drawPile.group, CostAction.REDUCE);
        setCardCosts(AbstractDungeon.player.discardPile.group, CostAction.REDUCE);
        setCardCosts(AbstractDungeon.player.exhaustPile.group, CostAction.REDUCE);
    }

    private void setCardCosts(ArrayList<AbstractCard> cards, CostAction action){
        for (AbstractCard c : cards) {
            if (!c.tags.contains(CustomTags.AERIAL)) {
                if (action == CostAction.REDUCE){
                    c.costForTurn = c.cost - 1;
                    c.isCostModifiedForTurn = true;
                } else {
                    c.costForTurn = c.cost;
                    c.isCostModifiedForTurn = false;
                }
            }
        }
    }
}
