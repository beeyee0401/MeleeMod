package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

import java.util.ArrayList;

public class TeamAttackOn extends CustomCard {
    private static final String ID = Constants.CardNames.TEAM_ATTACK_ON;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    public TeamAttackOn() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TeamAttackOn();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.target = CardTarget.ALL_ENEMY;
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        if (this.upgraded){
            for (AbstractMonster m : monsters) {
                friendlyFireAttack(m);
            }
        } else {
            friendlyFireAttack(monster);
        }
    }

    private void friendlyFireAttack(AbstractMonster attacker){
        if (attacker.getIntentBaseDmg() > 0) {
            AbstractMonster target = AbstractDungeon.getRandomMonster(attacker);
            int damage = attacker.getIntentDmg();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(attacker, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}
