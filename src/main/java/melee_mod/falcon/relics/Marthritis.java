package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.MARTHRITIS;

public class Marthritis extends CustomRelic {
    private static final String ID = MARTHRITIS;

    public Marthritis() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Marthritis();
    }
}
