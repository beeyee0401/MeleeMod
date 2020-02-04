package falcon_mod.falcon.cards.keyword_card_helpers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import falcon_mod.falcon.powers.PercentPower;

public class PercentCardHelper {
    public static void applyPercent(AbstractCreature source, AbstractCreature target, int amount){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new PercentPower(target, amount)));
    }
}
