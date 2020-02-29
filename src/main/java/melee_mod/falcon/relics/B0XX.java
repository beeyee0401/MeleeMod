package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.Shine;

import java.util.ArrayList;

public class B0XX extends CustomRelic {
    private static final String ID = Constants.Relics.B0XX;

    public B0XX() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new B0XX();
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
        }

        for(i = 0; i < 5; ++i) {
            AbstractCard c = new Shine();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }
}
