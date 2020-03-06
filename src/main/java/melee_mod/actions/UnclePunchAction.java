package melee_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class UnclePunchAction extends AbstractGameAction {
    private final ArrayList<AbstractCard.CardType> cardTypes = new ArrayList<>();
    private final ArrayList<AbstractCard> upgradedCards = new ArrayList<>();

    public UnclePunchAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.cardTypes.add(AbstractCard.CardType.ATTACK);
        this.cardTypes.add(AbstractCard.CardType.POWER);
        this.cardTypes.add(AbstractCard.CardType.SKILL);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            ArrayList<AbstractCard> possibleCards = new ArrayList<>();
            ArrayList<AbstractCard> allCards = new ArrayList<>();
            allCards.addAll(AbstractDungeon.player.hand.group);
            allCards.addAll(AbstractDungeon.player.drawPile.group);

            for (AbstractCard.CardType type: this.cardTypes) {
                possibleCards.clear();
                for (AbstractCard c: allCards) {
                    if (c.type == type && c.canUpgrade()){
                        possibleCards.add(c);
                    }
                }

                if (!possibleCards.isEmpty()) {
                    AbstractCard c = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    c.upgrade();
                    c.superFlash();
                    upgradedCards.add(c);
                }
            }
        }

        this.tickDuration();
        if (this.isDone) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }
}
