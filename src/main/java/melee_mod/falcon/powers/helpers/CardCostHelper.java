package melee_mod.falcon.powers.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import globals.Enums;

import java.util.ArrayList;

public class CardCostHelper {
    public static void setCardCostByTag(ArrayList<AbstractCard> cards, Enums.CostAction action, int difference, AbstractCard.CardTags tag, boolean exclude){
        for (AbstractCard c : cards) {
            if ((!exclude && c.tags.contains(tag)) ||
                    (exclude && !c.tags.contains(tag))) {
                setCardCost(c, action, difference);
            }
        }
    }

    public static void setCardCostByTag(ArrayList<AbstractCard> cards, Enums.CostAction action, int difference, AbstractCard.CardTags tag){
        setCardCostByTag(cards, action, difference, tag, false);
    }

    public static void setCardCostByKeywordAndType(ArrayList<AbstractCard> cards, Enums.CostAction action, int difference, AbstractCard.CardType type, String keyword){
        for (AbstractCard c : cards) {
            if (c.type == type && (c.keywords.contains(keyword) || c.keywords.contains(keyword.toLowerCase()))) {
                setCardCost(c, action, difference);
            }
        }
    }

    private static void setCardCost(AbstractCard c, Enums.CostAction action, int difference){
        if (action == Enums.CostAction.REDUCE){
            c.costForTurn = Math.max((c.cost - difference), 0);
            c.isCostModifiedForTurn = true;
        } else if (action == Enums.CostAction.INCREASE) {
            c.costForTurn = c.cost + difference;
            c.isCostModifiedForTurn = true;
        } else {
            resetCardCost(c);
        }
    }

    public static void resetAllCardCosts(){
        resetCardCosts(AbstractDungeon.player.hand.group);
        resetCardCosts(AbstractDungeon.player.drawPile.group);
        resetCardCosts(AbstractDungeon.player.discardPile.group);
        resetCardCosts(AbstractDungeon.player.exhaustPile.group);
    }

    public static void resetCardCosts(ArrayList<AbstractCard> cards){
        for (AbstractCard c : cards) {
            resetCardCost(c);
        }
    }

    public static void resetCardCost(AbstractCard c) {
        c.costForTurn = c.cost;
        c.isCostModifiedForTurn = false;
    }
}
