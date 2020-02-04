package falcon_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import falcon_mod.FalconCharacterMod;
import globals.Constants;

import static falcon_mod.falcon.patches.CustomTags.AERIAL;

public class AirWobblingPower
        extends AbstractPower {
    public static final String POWER_ID = Constants.Powers.AIR_WOBBLING;
    public static final String NAME = "Air Wobbling";
    public static final String DESCRIPTION = "Add an additional combo-point to each of your Aerial cards";
    public AirWobblingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.BUFF;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION;
    }

    @Override
    public void onUseCard(com.megacrit.cardcrawl.cards.AbstractCard card, com.megacrit.cardcrawl.actions.utility.UseCardAction action) {
    }
}
