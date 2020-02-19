package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.COMBO_POINTS;
import static globals.Constants.Powers.CROWD_PLEASER;

public class CrowdPleaserPower extends AbstractPower {
    public static final String POWER_ID = CROWD_PLEASER;
    public static final String NAME = "Crowd Pleaser";
    public CrowdPleaserPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        this.description = "When a Finisher is used on a target that has Combo-points, gain " + (this.amount * 2) + " Strength.";
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (action.target != null && action.target.hasPower(COMBO_POINTS) &&
                (card.keywords.contains(Constants.Keywords.FINISHER) || card.keywords.contains(Constants.Keywords.FINISHER.toLowerCase()))){
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount * 2)));
        }
    }
}
