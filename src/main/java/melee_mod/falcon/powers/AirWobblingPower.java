package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import melee_mod.FalconCharacterMod;
import globals.Constants;

public class AirWobblingPower
        extends AbstractPower {
    private static final String POWER_ID = Constants.Powers.AIR_WOBBLING;
    private static final String NAME = "Air Wobbling";
    private static final String DESCRIPTION = "Add an additional combo-point to each of your Aerial cards";
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
}
