package melee_mod.falcon.cards.keyword_card_helpers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import globals.Constants;

public class FinisherCardHelper {
    public static void removeComboPoints(AbstractCreature monster) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(monster, monster, Constants.Powers.COMBO_POINTS));
//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, monster, Constants.Powers.COMBO_POINTS));
    }
}
