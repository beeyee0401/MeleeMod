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

import static globals.Constants.Powers.L_CANCELED;

public class LCanceledPower extends AbstractPower {
    private static final String POWER_ID = L_CANCELED;
    private static final String NAME = "Just L-Canceled";

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
        this.description = "Cost of your next non-Aerial card is reduced by " + this.amount + " [E]";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.tags.contains(CustomTags.AERIAL)){
            CardCostHelper.resetAllCardCosts();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        int reduction = this.amount;
        CardCostHelper.setCardCostByTag(AbstractDungeon.player.hand.group, Enums.CostAction.REDUCE, reduction, CustomTags.AERIAL, true);
        CardCostHelper.setCardCostByTag(AbstractDungeon.player.drawPile.group, Enums.CostAction.REDUCE, reduction, CustomTags.AERIAL, true);
        CardCostHelper.setCardCostByTag(AbstractDungeon.player.discardPile.group, Enums.CostAction.REDUCE, reduction, CustomTags.AERIAL, true);
        CardCostHelper.setCardCostByTag(AbstractDungeon.player.exhaustPile.group, Enums.CostAction.REDUCE, reduction, CustomTags.AERIAL, true);
    }
}
