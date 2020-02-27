package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;

import static globals.Constants.Powers.COMBO_POINTS;
import static globals.Constants.Powers.CROWD_PLEASER;

public class CrowdPleaserPower extends AbstractPower {
    private static final String POWER_ID = CROWD_PLEASER;
    private static final String NAME = "Crowd Pleaser";
    public CrowdPleaserPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "When a Finisher is used on a target that has Combo-points, gain " + (this.amount * 2) + " Strength.";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            boolean isFinisher = card.keywords.contains(Constants.Keywords.FINISHER) || card.keywords.contains(Constants.Keywords.FINISHER.toLowerCase());
            boolean hasComboFinisherPower = AbstractDungeon.player.hasPower(Constants.Powers.COMBO_FINISHER);
            boolean isComboCard = ComboCardHelper.isComboCard(card, action);
            if (action.target != null && action.target.hasPower(COMBO_POINTS) &&
                    (isFinisher || (hasComboFinisherPower && isComboCard))) {
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount * 2)));
            }
        }
    }
}
