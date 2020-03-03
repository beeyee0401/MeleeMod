package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.CustomTags;

import static globals.Constants.Powers.L_CANCELED;
import static globals.Constants.Powers.L_CANCELING;

public class LCancelingPower extends AbstractPower {
    private static final String POWER_ID = L_CANCELING;
    private static final String NAME = "Learned to L-Cancel";
    public LCancelingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "After using an Aerial, reduce the cost of your next card by " + this.amount + " [E] if it's a non-Aerial.";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.tags.contains(CustomTags.AERIAL) &&
                !this.owner.hasPower(L_CANCELED)){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new LCanceledPower(this.owner, this.amount)));
        }
    }
}
