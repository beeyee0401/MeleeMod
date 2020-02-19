package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.LOSE_BUFFER;

public class LoseBufferPower extends AbstractPower {
    public static final String POWER_ID = LOSE_BUFFER;
    public static final String NAME = "Lose Buffer";
    public LoseBufferPower(AbstractCreature owner, int amount) {
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
        this.description = "Lose " + this.amount + " Buffer(s) at the end of the turn";
    }

    public void atEndOfRound() {
        this.addToBot(new ReducePowerAction(this.owner, this.owner, BufferPower.POWER_ID, this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
