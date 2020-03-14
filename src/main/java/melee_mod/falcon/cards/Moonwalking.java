package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.MoonwalkingPower;
import melee_mod.falcon.powers.PercentPlusPower;

public class Moonwalking extends CustomCard {
    private static final String ID = Constants.CardNames.MOONWALKING;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_ENERGY = 1;
    private static final int BASE_PERCENT = 10;
    private static final int UPGRADE_PERCENT = -5;

    public Moonwalking() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.FALCON_BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = BASE_PERCENT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        this.addToBot(new ApplyPowerAction(p, p, new MoonwalkingPower(p, BASE_ENERGY)));
        this.addToBot(new ApplyPowerAction(p, p, new PercentPlusPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Moonwalking();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PERCENT);
        }
    }
}
