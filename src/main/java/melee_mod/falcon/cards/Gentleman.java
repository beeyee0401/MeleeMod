package falcon_mod.falcon.cards;

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
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

// This card has a jank implementation because you can't have multiple attack values
public class Gentleman extends CustomCard {
    private static final String ID = Constants.CardNames.GENTLEMAN;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int WEAK_ATTACK_DAMAGE = 2;
    private static final int STRONG_ATTACK_DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int WEAK_HITS = 2;

    public Gentleman() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.FALCON_BLUE, CardRarity.COMMON,
                AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = WEAK_ATTACK_DAMAGE;
        this.magicNumber = this.baseMagicNumber = STRONG_ATTACK_DAMAGE;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gentleman();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < WEAK_HITS; i++) {
            DamageInfo info = new DamageInfo(player, damage, damageTypeForTurn);
            DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            AbstractDungeon.actionManager.addToBottom(action);
        }
        DamageInfo info = new DamageInfo(player, this.magicNumber, damageTypeForTurn);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
