package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import globals.Constants;

import static globals.Constants.Powers.PERCENT;

// add angel platform power at 100%
public class PercentPower extends AbstractPower {
    public static final String POWER_ID = PERCENT;
    public static final String NAME = "Percent";
    public PercentPower(AbstractCreature owner, int amount) {
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
        this.description = "Takes " + this.amount + "% more damage from attacks";
    }

    @Override
    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType, com.megacrit.cardcrawl.cards.AbstractCard card){
        if (card.keywords.contains(Constants.Keywords.FINISHER)){
            damage += (damage * this.amount);
            this.amount = 0;
        }
        return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        System.out.println("on use card");
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        System.out.println("on play card");
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount){
        System.out.println("on attacked");
        return damageAmount;
    }
}
