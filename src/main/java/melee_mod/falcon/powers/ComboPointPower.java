package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import melee_mod.FalconCharacterMod;
import globals.Constants;

import static globals.Constants.Powers.COMBO_POINTS;

public class ComboPointPower
        extends AbstractPower {
    public static final String POWER_ID = COMBO_POINTS;
    public static final String NAME = "Combo-Points";
    public static final String DESCRIPTION = "When using a Finisher on this target, multiply the damage by 25% for each combo-point consumed";
    public ComboPointPower(AbstractCreature owner, int amount) {
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
        this.description = DESCRIPTION;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card){
        if (card.keywords.contains(Constants.Keywords.FINISHER) || card.keywords.contains(Constants.Keywords.FINISHER.toLowerCase())){
            damage += damage * (this.amount * 0.25);
        }
        return damage;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager
                .addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner,
                        this.owner, this));
    }
}
