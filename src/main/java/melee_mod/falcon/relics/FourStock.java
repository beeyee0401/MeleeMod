package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.FOUR_STOCK;

public class FourStock extends CustomRelic {
    private static final String ID = FOUR_STOCK;
    private boolean triggered = false;

    public FourStock() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FourStock();
    }

    @Override
    public void update() {
        super.update();
        if (triggered && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            triggered = false;
        }
    }

    @Override
    public void onVictory() {
        CardGroup upgradeableCards = AbstractDungeon.player.masterDeck.getUpgradableCards();
        if (AbstractDungeon.player.damagedThisCombat == 0 && upgradeableCards.size() > 0) {
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.gridSelectScreen.open(upgradeableCards, 1, "Upgrade a card", true, false, false, false);
            AbstractDungeon.gridSelectScreen.peekButton.hide();
            triggered = true;
        }
    }
}
