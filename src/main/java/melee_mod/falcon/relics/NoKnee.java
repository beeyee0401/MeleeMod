package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.CrowdPleaserPower;

import static globals.Constants.Relics.NO_KNEE;

public class NoKnee extends CustomRelic {
    private static final String ID = NO_KNEE;

    public NoKnee() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NoKnee();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new CrowdPleaserPower(p, 2)));
    }
}
