package falcon_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.CustomTags;

import static globals.Constants.Powers.LCANCELED;
import static globals.Constants.Powers.LCANCELLING;

public class LCancellingPower extends AbstractPower {
    private static final String POWER_ID = LCANCELLING;
    private static final String NAME = "Learned to L-Cancel";
    private static final String DESCRIPTION = "After using an Aerial, reduce the cost of your next card by 1 [E] if it's a non-Aerial";
    public LCancellingPower(AbstractCreature owner, int amount) {
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
        if (card.tags.contains(CustomTags.AERIAL) &&
                !this.owner.hasPower(LCANCELED)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(action.target, action.source, new LCancelledPower(action.target, 1)));
        }
    }
}
