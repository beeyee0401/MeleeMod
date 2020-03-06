package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.PercentPower;

import static globals.Constants.Relics.NON_NEUTRAL_START;

public class NonNeutralStart extends CustomRelic {
    private static final String ID = NON_NEUTRAL_START;
    private static final int PERCENT = 15;

    public NonNeutralStart() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new PercentPower(m, PERCENT)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NonNeutralStart();
    }
}
