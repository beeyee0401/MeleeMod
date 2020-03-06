package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.LOSERS_MANG0;

public class LosersMang0 extends CustomRelic {
    private static final String ID = LOSERS_MANG0;
    private static final int BASE_BUFFS = 4;

    public LosersMang0() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        mainActions();
    }

    @Override
    public void atBattleStartPreDraw() {
        mainActions();
    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LosersMang0();
    }

    private void mainActions(){
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, BASE_BUFFS)));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, BASE_BUFFS)));
    }
}
