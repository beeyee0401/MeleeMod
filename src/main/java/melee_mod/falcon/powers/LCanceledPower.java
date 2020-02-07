package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Enums;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.helpers.CardCostHelper;

import static globals.Constants.Powers.LCANCELED;

public class LCanceledPower extends AbstractPower {
    private static final String POWER_ID = LCANCELED;
    private static final String NAME = "Just L-Canceled";
    private static final String DESCRIPTION = "Cost of your next non-Aerial card is reduced by 1 [E]";

    public LCanceledPower(AbstractCreature owner, int amount) {
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
            CardCostHelper.resetCardCosts(AbstractDungeon.player.hand.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.drawPile.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.discardPile.group);
            CardCostHelper.resetCardCosts(AbstractDungeon.player.exhaustPile.group);
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        int reduction = 1;
        CardCostHelper.setCardCosts(AbstractDungeon.player.hand.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.drawPile.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.discardPile.group, Enums.CostAction.REDUCE, reduction);
        CardCostHelper.setCardCosts(AbstractDungeon.player.exhaustPile.group, Enums.CostAction.REDUCE, reduction);
    }
}
