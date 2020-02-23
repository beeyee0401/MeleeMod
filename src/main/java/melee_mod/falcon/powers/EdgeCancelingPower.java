package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Enums;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.helpers.CardCostHelper;

import java.util.ArrayList;

import static globals.Constants.Powers.*;

public class EdgeCancelingPower extends AbstractPower {
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
        setCardGroup();
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
        AbstractPlayer p = AbstractDungeon.player;
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        if (p.hasPower(L_CANCELED)){
            p.getPower(L_CANCELED).onInitialApplication();
        }
    }

    @Override
    public void onInitialApplication() {
        int reduction = 1;
        CardCostHelper.setCardCosts(this.cardsToChange, Enums.CostAction.REDUCE, reduction);
    }

    private void setCardGroup(){
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.hand.group);
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : allCards) {
            if (c.tags.contains(CustomTags.AERIAL)){
                this.cardsToChange.add(c);
            }
        }
    }
}