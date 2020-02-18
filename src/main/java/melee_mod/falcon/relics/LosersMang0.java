package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.LOSERS_MANG0;

public class LosersMang0 extends CustomRelic {
    public static final String ID = LOSERS_MANG0;

    public LosersMang0() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void setCounter(int setCounter) {
        if (setCounter == -2) {
            this.usedUp();
            this.description = "At the start of combat, gain +4 Strength and +4 Dexterity";
            this.tips.clear();
            this.tips.add(new PowerTip("Loser's Mang0", this.description));
            this.initializeTips();
            this.counter = -2;
        }
    }

    @Override
    public void onTrigger() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 5)));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 5)));
        this.setCounter(-2);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.usedUp){
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 4)));
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 4)));
        } else {
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -1)));
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -1)));
        }
    }

    @Override
    public void onLoseHp(int damageAmount) {
        // Seems like the player.isDying field takes into account Res effects. Or it's just wrong, it's False here
        if (AbstractDungeon.player.currentHealth < damageAmount){
            this.onTrigger();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LosersMang0();
    }
}
