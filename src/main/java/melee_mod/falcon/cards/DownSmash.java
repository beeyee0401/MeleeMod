package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class DownSmash extends CustomCard {
    private static final String ID = Constants.CardNames.DOWN_SMASH;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int HIT_COUNT = 2;
    private static final int WEAK = 2;

    public DownSmash() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = WEAK;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DownSmash();
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
        for (int i = 0; i < HIT_COUNT; i++) {
            DamageInfo info = new DamageInfo(player, damage, damageTypeForTurn);
            DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            this.addToBot(action);
        }
        this.addToBot(new ApplyPowerAction(monster, player, new WeakPower(monster, this.magicNumber, false)));
    }
}
