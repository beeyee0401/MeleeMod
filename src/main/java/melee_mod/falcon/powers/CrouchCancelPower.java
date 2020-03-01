package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.CROUCH_CANCEL;

public class CrouchCancelPower extends AbstractPower {
    private static final String POWER_ID = CROUCH_CANCEL;
    private static final String NAME = "Crouch Cancel";

    public CrouchCancelPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "When attacked, apply " + this.amount + " Percent back to the attacker.";
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new ApplyPowerAction(info.owner, this.owner, new PercentPower(info.owner, this.amount)));
        }

        return damageAmount;
    }
}
