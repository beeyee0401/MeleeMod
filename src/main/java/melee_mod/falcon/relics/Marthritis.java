package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.MARTHRITIS;

public class Marthritis extends CustomRelic implements OnApplyPowerRelic {
    private static final String ID = MARTHRITIS;

    public Marthritis() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (target != p && source == p && power.ID.equals(Constants.Powers.PERCENT)){
            this.flash();
            power.amount*=2;
            stackAmount*=2;
        }
        return stackAmount;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Marthritis();
    }
}
