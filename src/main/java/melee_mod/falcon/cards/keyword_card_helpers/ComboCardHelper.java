package melee_mod.falcon.cards.keyword_card_helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.ComboPointPower;
import globals.Constants;

import java.util.ArrayList;

public class ComboCardHelper {
    public static void addComboPoint(AbstractMonster monster){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new ComboPointPower(monster, 1), 1));
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card) {
        doBaseAction(player, monster, card, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card, AbstractGameAction.AttackEffect attackEffect) {
        doBaseAction(player, monster, card.damage, card.magicNumber, card.damageTypeForTurn, card.tags, attackEffect);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, int damage, int hitCount,
                                    DamageInfo.DamageType damageType, ArrayList<AbstractCard.CardTags> tagsList,
                                    AbstractGameAction.AttackEffect attackEffect) {
        boolean shouldAddComboPoint = true;
        if (player.hasPower(Constants.Powers.COMBO_FINISHER) && monster.hasPower(Constants.Powers.COMBO_POINTS)) {
            shouldAddComboPoint = false;
        }

        int numHits = hitCount > 0 ? hitCount : 1;
        for (int i = 0; i < numHits; i++) {
            DamageInfo info = new DamageInfo(player, damage, damageType);
            DamageAction action = new DamageAction(monster, info, attackEffect);
            AbstractDungeon.actionManager.addToBottom(action);
        }

        if (shouldAddComboPoint){
            addComboPoint(monster);
            if (player.hasPower(Constants.Powers.AIR_WOBBLING) && tagsList.contains(CustomTags.AERIAL)){
                addComboPoint(monster);
            }
        }
    }

    public static boolean isComboCard(AbstractCard card){
        return card.keywords.contains(Constants.Keywords.COMBO) || card.keywords.contains(Constants.Keywords.COMBO.toLowerCase());
    }
}
