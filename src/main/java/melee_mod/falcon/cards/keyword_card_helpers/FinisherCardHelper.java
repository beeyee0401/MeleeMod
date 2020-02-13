package melee_mod.falcon.cards.keyword_card_helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.falcon.powers.ComboPointPower;
import globals.Constants;

public class FinisherCardHelper {
    public static float getFinisherDamage(float damage, AbstractMonster monster){
        if (monster != null) {
            if (monster.hasPower(ComboPointPower.POWER_ID)) {
                return (float) Math.round(damage * (0.25 * monster.getPower(ComboPointPower.POWER_ID).amount + 1));
            }
        }
        return damage;
    }

    public static void removeComboPoints(AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, monster, Constants.Powers.COMBO_POINTS));
    }
}
