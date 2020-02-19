package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.ClippedPower;

import static globals.Constants.Relics.POWER_CLIPPER;

public class PowerClipper extends CustomRelic {
    public static final String ID = POWER_CLIPPER;
    private static final int NUM_TURNS = 10;

    public PowerClipper() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }

        if (this.counter == NUM_TURNS) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, null, new ClippedPower(AbstractDungeon.player, 1), 1));
        }
    }

    public AbstractRelic makeCopy() {
        return new PowerClipper();
    }
}
