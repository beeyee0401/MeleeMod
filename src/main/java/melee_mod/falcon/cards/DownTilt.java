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
import falcon_mod.falcon.cards.keyword_card_helpers.PercentCardHelper;
import falcon_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class DownTilt extends CustomCard {
    private static final String ID = Constants.CardNames.DOWN_SMASH;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_DAMAGE = 9;
    private static final int BASE_PERCENT = 10;
    private static final int UPGRADE_DAMAGE = 3;

    public DownTilt() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = BASE_PERCENT;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DownTilt();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DamageInfo info = new DamageInfo(player, damage, damageTypeForTurn);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractDungeon.actionManager.addToBottom(action);

        PercentCardHelper.applyPercent(player, monster, this.magicNumber);
    }
}
