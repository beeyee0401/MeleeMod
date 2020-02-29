package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;

public class BMoveSpecialistPower extends AbstractPower {
    private static final String POWER_ID = Constants.Powers.B_MOVE_SPECIALIST;
    private static final String NAME = "B Move Specialist";

    public BMoveSpecialistPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        String amountStr = this.amount + " additional times";
        if (this.amount == 1){
            amountStr = this.amount + " additional time";
        }
        this.description = "Burn triggers " + amountStr + " at the end of the turn.";
    }
}
