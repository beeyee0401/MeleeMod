package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.powers.JohnnyStockPower;

import static globals.Constants.Relics.JOHNNY_STOCK;

public class JohnnyStock extends CustomRelic {
    private static final String ID = JOHNNY_STOCK;
    private final int STRENGTH_BUFF = 5;
    private final int JOHNNY_STOCK_BUFF = 1;
    private boolean alreadyApplied;

    public JohnnyStock() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!isAboveTwentyPercentHealth(p.currentHealth)){
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_BUFF)));
            this.addToBot(new ApplyPowerAction(p, p, new JohnnyStockPower(p, JOHNNY_STOCK_BUFF)));
            alreadyApplied = true;
        }
    }

    @Override
    public void onLoseHp(int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!alreadyApplied && !isAboveTwentyPercentHealth(Math.max(p.currentHealth - damageAmount, 0))){
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_BUFF)));
            this.addToBot(new ApplyPowerAction(p, p, new JohnnyStockPower(p, JOHNNY_STOCK_BUFF), JOHNNY_STOCK_BUFF));
            alreadyApplied = true;
        }
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (alreadyApplied && isAboveTwentyPercentHealth(p.currentHealth + healAmount)){
            this.addToBot(new RemoveSpecificPowerAction(p, p, Constants.Powers.JOHNNY_STOCK));
            this.addToBot(new ReducePowerAction(p, p, StrengthPower.POWER_ID, STRENGTH_BUFF));
            alreadyApplied = false;
        }
        return healAmount;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new JohnnyStock();
    }

    private boolean isAboveTwentyPercentHealth(int health){
        return ((double) health / AbstractDungeon.player.maxHealth) > 0.2;
    }
}
