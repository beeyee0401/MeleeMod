package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.LCancelingPower;

import static globals.Constants.Relics.AUTO_L_CANCELING;

public class AutoLCanceling extends CustomRelic {
    public static final String ID = AUTO_L_CANCELING;

    public AutoLCanceling() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToTop(new RelicAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, null, new LCancelingPower(p, 1)));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AutoLCanceling();
    }
}