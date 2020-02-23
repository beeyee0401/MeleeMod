package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
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

public class LCanceledPower extends AbstractPower {
    private static final String POWER_ID = L_CANCELED;
    private static final String NAME = "Just L-Canceled";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();

    public LCanceledPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        setCardGroup();
    }

    @Override
    public void updateDescription() {
        this.description = "Cost of your next non-Aerial card is reduced by " + this.amount + " [E]";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.tags.contains(CustomTags.AERIAL)){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onRemove() {
        AbstractPlayer p = AbstractDungeon.player;
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        if (p.hasPower(EDGE_CANCELING)){
            p.getPower(EDGE_CANCELING).onInitialApplication();
        }
    }

    @Override
    public void onInitialApplication() {
        int reduction = this.amount;
        CardCostHelper.setCardCosts(this.cardsToChange, Enums.CostAction.REDUCE, reduction);
    }

    private void setCardGroup(){
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.hand.group);
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : allCards) {
            if (!c.tags.contains(CustomTags.AERIAL)){
                this.cardsToChange.add(c);
            }
        }
    }
}
