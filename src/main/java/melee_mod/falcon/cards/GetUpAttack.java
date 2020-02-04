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
import melee_mod.falcon.cards.keyword_card_helpers.PercentCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class GetUpAttack extends CustomCard {
    private static final String ID = Constants.CardNames.GET_UP_ATTACK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int MAGIC_NUMBER = 2;

    public GetUpAttack() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public AbstractCard makeCopy() {
        return new GetUpAttack();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = "Deal !D! damage !M! times. Apply " + (this.damage * this.magicNumber) + " Percent to the target.";
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; i++) {
            DamageInfo info = new DamageInfo(player, damage, damageTypeForTurn);
            DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            AbstractDungeon.actionManager.addToBottom(action);
        }
        if (this.upgraded){
            PercentCardHelper.applyPercent(player, monster, this.damage * this.magicNumber);
        } else {
            PercentCardHelper.applyPercent(player, player, this.damage);
        }
    }
}
