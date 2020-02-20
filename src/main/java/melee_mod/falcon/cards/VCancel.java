package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.actions.VCancelAction;
import melee_mod.falcon.patches.AbstractCardEnum;

public class VCancel extends CustomCard {
    private static final String ID = Constants.CardNames.V_CANCEL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_DRAW = 1;
    private static final int UPGRADE_DRAW = 1;

    public VCancel() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = BASE_DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(this.magicNumber,  new VCancelAction(1)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VCancel();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DRAW);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
