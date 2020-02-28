package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.B_MOVE_USER;

public class BMoveUser extends CustomRelic implements OnApplyPowerRelic {
    private static final String ID = B_MOVE_USER;

    public BMoveUser() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.ID.equals(Constants.Powers.BURN)){
            this.flash();
            power.amount++;
            stackAmount++;
        }
        return stackAmount;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BMoveUser();
    }
}
