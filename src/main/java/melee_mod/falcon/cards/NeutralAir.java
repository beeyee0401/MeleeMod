package falcon_mod.falcon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import falcon_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

import static falcon_mod.falcon.patches.CustomTags.AERIAL;

public class NeutralAir extends CustomCard {
    public static final String ID = Constants.CardNames.NEUTRAL_AIR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int TIMES = 2;

    public NeutralAir() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = TIMES;
        this.tags.add(AERIAL);
    }

    @Override
    public AbstractCard makeCopy() {
        return new NeutralAir();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ComboCardHelper.doBaseAction(abstractPlayer, abstractMonster, this);
    }
}
