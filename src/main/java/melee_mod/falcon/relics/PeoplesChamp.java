package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.CommentatorsCurse;

import static globals.Constants.Relics.PEOPLES_CHAMP;

public class PeoplesChamp extends CustomRelic {
    public static final String ID = PEOPLES_CHAMP;
    private static final int NUM_TURNS = 6;

    public PeoplesChamp() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
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
            this.addToBot(new MakeTempCardInHandAction(new CommentatorsCurse(), 1, false));
        }
    }
}
