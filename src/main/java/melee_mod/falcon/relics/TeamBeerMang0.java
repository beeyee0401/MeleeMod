package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.Mang0Falco;
import melee_mod.falcon.cards.Mang0Fox;

import static globals.Constants.Relics.TEAM_BEER_MANG0;

public class TeamBeerMang0 extends CustomRelic {
    private static final String ID = TEAM_BEER_MANG0;

    public TeamBeerMang0() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.addToBot(new MakeTempCardInHandAction(new Mang0Falco(), 1, false));
        this.addToBot(new MakeTempCardInHandAction(new Mang0Fox(), 1, false));
    }

    @Override
    public void onLoseHp(int damageAmount) {
        // Seems like the player.isDying field takes into account Res effects. Or it's just wrong, it's False here
        if (AbstractDungeon.player.currentHealth <= damageAmount){
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new RelicAboveCreatureAction(p, this));
            int index = p.relics.indexOf(this);
            AbstractRelic losersMang0 = new LosersMang0();
            losersMang0.instantObtain(AbstractDungeon.player, index, true);
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TeamBeerMang0();
    }
}
