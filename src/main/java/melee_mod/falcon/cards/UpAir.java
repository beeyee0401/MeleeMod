package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;

import static melee_mod.falcon.patches.CustomTags.AERIAL;

public class UpAir extends CustomCard {
    private static final String ID = Constants.CardNames.UP_AIR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;

    public UpAir() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.tags.add(AERIAL);
    }

    @Override
    public AbstractCard makeCopy() {
        return new UpAir();
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
