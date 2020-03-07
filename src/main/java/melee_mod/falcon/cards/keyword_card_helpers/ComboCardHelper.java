package melee_mod.falcon.cards.keyword_card_helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.ComboPointPower;
import globals.Constants;

import java.util.ArrayList;

public class ComboCardHelper {
    public static void addComboPoint(AbstractCreature monster){
        addComboPoint(monster, 1);
    }

    public static void addComboPoint(AbstractCreature monster, int num){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new ComboPointPower(monster, num, false)));
    }

    public static void addComboPointByComboAndFinisher(AbstractCreature monster, int num){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new ComboPointPower(monster, num, true)));
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card) {
        doBaseAction(player, monster, card, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, 0);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card, AbstractGameAction.AttackEffect attackEffect) {
        doBaseAction(player, monster, card.damage, card.magicNumber, card.damageTypeForTurn, card.tags, attackEffect, 0);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, CustomCard card, AbstractGameAction.AttackEffect attackEffect, int additionalComboPoints) {
        doBaseAction(player, monster, card.damage, card.magicNumber, card.damageTypeForTurn, card.tags, attackEffect, additionalComboPoints);
    }

    public static void doBaseAction(AbstractPlayer player, AbstractMonster monster, int damage, int hitCount,
                                    DamageInfo.DamageType damageType, ArrayList<AbstractCard.CardTags> tagsList,
                                    AbstractGameAction.AttackEffect attackEffect, int additionalComboPoints) {
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
            int points = 1 + additionalComboPoints;
            if (player.hasPower(Constants.Powers.AIR_WOBBLING) && tagsList.contains(CustomTags.AERIAL)){
                points++;
            }
            addComboPoint(monster, points);
        }
    }

    public static boolean isComboCard(AbstractCard card, UseCardAction action){
        if (card.keywords.contains(Constants.Keywords.COMBO) || card.keywords.contains(Constants.Keywords.COMBO.toLowerCase())){
            // Knee is only a combo card if they have no Combo Points
            if (card.cardID.equals(Constants.CardNames.KNEE) &&
                    action.target != null && action.target.hasPower(Constants.Powers.COMBO_POINTS)){
                return false;
            }
            return true;
        }
        return false;
    }
}
