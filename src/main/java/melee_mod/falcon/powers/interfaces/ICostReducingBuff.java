package melee_mod.falcon.powers.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public interface ICostReducingBuff {
    int getReduction();
    ArrayList<AbstractCard> getCardsToChange();
    boolean shouldAddToCardGroup(AbstractCard c);
    default void setCardGroup(){
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.hand.group);
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.exhaustPile.group);
        ArrayList<AbstractCard> cardsToChange = this.getCardsToChange();
        for (AbstractCard c : allCards) {
            if (!cardsToChange.contains(c) && this.shouldAddToCardGroup(c)){
                cardsToChange.add(c);
            }
        }
    }
}
