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

public class Shine extends CustomCard {
    private static final String ID = Constants.CardNames.SHINE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Shine() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);

        this.damage = this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ComboCardHelper.doBaseAction(p, m, this);
    }

    public AbstractCard makeCopy() {
        return new Shine();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
