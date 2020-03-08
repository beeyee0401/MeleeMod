package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.helpers.CardCostHelper;
import melee_mod.falcon.powers.interfaces.ICostReducingBuff;

import java.util.ArrayList;

import static globals.Constants.Powers.*;
import static globals.Enums.CostAction.REDUCE;

public class EdgeCancelingPower extends AbstractPower implements ICostReducingBuff {
    private static final String POWER_ID = EDGE_CANCELING;
    private static final String NAME = "Edge Canceling";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();

    public EdgeCancelingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.setCardGroup();
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
    public void onRemove() {
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        CardCostHelper.initializeBuffCosts(this);
    }

    @Override
    public void onInitialApplication() {
        CardCostHelper.setCardCosts(this.cardsToChange, REDUCE, 1);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (c.tags.contains(CustomTags.AERIAL) && !c.isCostModifiedForTurn) {
            CardCostHelper.initializeBuffCostsForCard(c);
        }
    }

    @Override
    public boolean shouldAddToCardGroup(AbstractCard c){
        return c.tags.contains(CustomTags.AERIAL);
    }

    @Override
    public int getReduction(){
        return 1;
    }

    @Override
    public ArrayList<AbstractCard> getCardsToChange() {
        return this.cardsToChange;
    }
}