package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.PercentCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.PercentPower;

public class BlueFalcon extends CustomCard {
    private static final String ID = Constants.CardNames.BLUE_FALCON;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_DAMAGE = 25;
    private static final int PERCENT = 35;
    private static final int UPGRADE_COST = 2;

    public BlueFalcon() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = PERCENT;
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlueFalcon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new DamageAllEnemiesAction(player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            PercentCardHelper.applyPercent(player, m, this.magicNumber);
        }
    }
}
