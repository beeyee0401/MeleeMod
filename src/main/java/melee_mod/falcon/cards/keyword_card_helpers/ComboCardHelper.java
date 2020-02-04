package falcon_mod.falcon.cards.keyword_card_helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import falcon_mod.falcon.patches.CustomTags;
import falcon_mod.falcon.powers.ComboPointPower;
import falcon_mod.falcon.powers.PercentPower;
import globals.Constants;

public class ComboCardHelper {
    public static void addComboPoint(AbstractMonster monster){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new ComboPointPower(monster, 1), 1));
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card) {
        doBaseAction(player, monster, card, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card, AbstractGameAction.AttackEffect attackEffect) {
        boolean shouldAddComboPoint = true;
        if (player.hasPower(Constants.Powers.COMBO_FINISHER) && monster.hasPower(Constants.Powers.COMBO_POINTS)) {
            card.damage = (int) FinisherCardHelper.getFinisherDamage(card.damage, monster);
            shouldAddComboPoint = false;
        }

        int hitCount = card.magicNumber > 0 ? card.magicNumber : 1;
        for (int i = 0; i < hitCount; i++) {
            DamageInfo info = new DamageInfo(player, card.damage, card.damageTypeForTurn);
            DamageAction action = new DamageAction(monster, info, attackEffect);
            AbstractDungeon.actionManager.addToBottom(action);
        }

        if (shouldAddComboPoint){
            addComboPoint(monster);
            if (player.hasPower(Constants.Powers.AIR_WOBBLING) && card.tags.contains(CustomTags.AERIAL)){
                addComboPoint(monster);
            }
        }
    }

    public static boolean isComboCard(AbstractCard card){
        return card.keywords.contains(Constants.Keywords.COMBO) || card.keywords.contains(Constants.Keywords.COMBO.toLowerCase());
    }
}
