package falcon_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import globals.Constants;

public class ComboFinisherPower extends AbstractPower {
    public static final String POWER_ID = Constants.Powers.COMBO_FINISHER;
    public static final String NAME = "Combo Finisher";
    public ComboFinisherPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        int percent = 10 * amount;
        this.description = "If the target has combo-points, your next combo card becomes a finisher and applies an additional " + percent + "%";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (ComboCardHelper.isComboCard(card) && card.type == AbstractCard.CardType.ATTACK && action.target.hasPower(Constants.Powers.COMBO_POINTS)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(action.target, action.source, new PercentPower(action.target, 10 * this.amount)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(action.target, action.target, Constants.Powers.COMBO_POINTS));
        }
    }
}
