package melee_mod.falcon.powers.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import globals.Enums;
import melee_mod.falcon.patches.CustomTags;

import java.util.ArrayList;

public class CardCostHelper {
    public static void setCardCosts(ArrayList<AbstractCard> cards, Enums.CostAction action, int difference){
        for (AbstractCard c : cards) {
            if (!c.tags.contains(CustomTags.AERIAL)) {
                if (action == Enums.CostAction.REDUCE){
                    c.costForTurn = c.cost - difference;
                    c.isCostModifiedForTurn = true;
                } else if (action == Enums.CostAction.INCREASE) {
                    c.costForTurn = c.cost + difference;
                    c.isCostModifiedForTurn = true;
                } else {
                    resetCardCost(c);
                }
            }
        }
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
