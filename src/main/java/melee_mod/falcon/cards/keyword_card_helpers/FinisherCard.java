package melee_mod.falcon.cards.keyword_card_helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import globals.Constants;
import melee_mod.falcon.powers.ComboPointPower;

public abstract class FinisherCard extends CustomCard {
    public FinisherCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void triggerWhenDrawn() {
        if (!this.isCostModifiedForTurn){
            ComboPointPower.initializeComboPointCostForCard(this);
        }
    }

    public static void removeComboPoints(AbstractCreature monster) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(monster, monster, Constants.Powers.COMBO_POINTS));
    }
}
